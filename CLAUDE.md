# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A Selenium + TestNG UI test-automation suite for Jatri (jatri.co / dev-jatri.jatritech.com), a bus/car travel booking platform. There is no application source here — `src/main/java/org/example/Main.java` is an unused IntelliJ scaffold file, not part of the test suite. All real code lives under `src/test/java`.

## Commands

Run the full suite:
```
./gradlew test
```

Run a single test class:
```
./gradlew test --tests "user.co.jatri.test.services.bus.PurchaseSingleSeatWithBkashTest"
```

Run a single test method:
```
./gradlew test --tests "user.co.jatri.test.services.bus.PurchaseSingleSeatWithBkashTest.purchaseSingleSeatWithBkash"
```

Build only (compiles main + test sources):
```
./gradlew build
```

Note: `src/test/resources/test-runner.xml` defines a TestNG suite over the `user.co.jatri.test` package, but `build.gradle`'s `test { useTestNG() }` block does not reference it via `suiteXmlFiles`. Gradle's default TestNG integration auto-discovers `@Test` methods directly, so this XML file is not currently wired into `./gradlew test` — don't assume editing it changes what runs.

CI (`.github/workflows/gradle.yml`) runs on `windows-latest` with JDK 17 (Temurin) and simply calls `./gradlew test`; the scheduled cron trigger is currently commented out.

## Configuration and secrets

- `src/test/resources/config.properties` — browser choice (`browserName`: `chrome`/`firefox`/`edge`/`safari`, or a `-headless` variant of each) and base URL (`devBaseUrl`/`liveBaseUrl`, only one should be uncommented) plus test mobile numbers.
- `.env` (gitignored, loaded via `dotenv-java`) — holds `MONGO_URL`/`MONGO_DATABASE_NAME`/`MONGO_COLLECTION_NAME` for OTP lookup and `BKASH_NUMBER`/`BKASH_OTP`/`BKASH_PIN` for the bKash payment sandbox flow. Tests generally check `System.getenv(...)` first and fall back to the `.env`-loaded value.
- `config.properties` also happens to contain its own `BKASH_NUMBER`/`BKASH_OTP`/`BKASH_PIN` entries, but no active code path reads them (the code that would have is commented out in `PurchaseSingleSeatWithBkashTest`) — `.env`/env vars are the ones that actually matter; don't assume editing `config.properties` changes bKash behavior.

## Architecture: Page Object Model with fluent chaining

The core pattern to understand before touching pages or tests is the `Page` → `BasePage` → feature page hierarchy in `src/test/java/user/co/jatri/pages/`:

- `Page` (abstract, `pages/base/Page.java`) holds the shared `WebDriver`/`WebDriverWait` and declares the element-interaction contract (`getWebElement`, `clickElement`, `setWait`, etc.) as abstract methods. It also provides `goTo(Class<T> pageClass)`, which reflectively constructs any `BasePage` subclass with the same `driver` instance — this is what makes page transitions type-safe without a shared state object.
- `BasePage` (`pages/base/BasePage.java`) implements the actual Selenium calls and also logs every element lookup/click/wait to the Extent report via `addInfo`/`addFailInfo`.
- Feature pages (e.g. `pages/home/HomePage.java`, `pages/auth/LoginPage.java`, `pages/services/bus/*`) extend `BasePage`. Each action method performs a Selenium interaction and returns either `goTo(SameOrNextPage.class)` for chaining, or a primitive (`boolean`/`String`) for assertions.

This lets tests read as a single fluent chain, e.g. in `PurchaseSingleSeatWithBkashTest`:
```java
page.goTo(HomePage.class)
    .clickAcceptAllCookiesBtn()
    .navigateToSearchBookingPage()
    .selectLeavingFrom("Dhaka")
    ...
    .navigateToBookingSuccessOrFailedPage();
```
When adding a new page/flow step, follow this shape: the method belongs on the *current* page class, and its return type is whatever page the UI actually navigates to next (or the same class if it stays put).

## Test lifecycle

- Tests extend `test/base/BaseTest.java`, which reads `config.properties` in its constructor, then in `@BeforeMethod` launches the configured `WebDriver`, maximizes the window, navigates to the configured base URL, and seeds `page` as a fresh `BasePage`.
- `@AfterMethod` quits the driver and, on failure, screenshots to `build/screenshots/`.
- OTP-based logins don't use SMS: `pages/auth/FindOtp.java` connects directly to MongoDB (via `.env` credentials) and polls for the most recent OTP document matching the phone number (see `LoginPage.setOtp`).

## Reporting

ExtentReports is wired through `report/ReportManager.java` (singleton report instance → `build/extendReport/Report.html`) and `report/ReportTestManager.java` (maps the current thread ID to an `ExtentTest`, since `BasePage.addInfo`/`addFailInfo` need to log against the in-flight test from arbitrary page-object code). `report/TestListener.java` implements `ITestListener` to start/finish tests and capture failure screenshots into the report, but it is not currently registered anywhere (no `@Listeners` annotation, no `<listeners>` entry in `test-runner.xml`) — treat it as effectively dead code unless you wire it in.
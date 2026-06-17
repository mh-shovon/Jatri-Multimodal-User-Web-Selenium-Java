package user.co.jatri.utils;

public class MultimodalUserUtil {
    final public static int WAIT_TIME = 30;

    public static final String HOME_PAGE_TITLE = "Jatri - One Search to Travel Smarter";

    public static final String LOGIN_PAGE_TITLE = "Jatri - One Search to Travel Smarter";

    public static final String CAR_TRIP_LIST_PAGE_TITLE = "Explore Car Options - One Search to Travel Smarter";

    public static final String BUS_TRIP_LIST_PAGE_TITLE = "Choose Your Bus - One Search to Travel Smarter";

    public static void waitForDomStable(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

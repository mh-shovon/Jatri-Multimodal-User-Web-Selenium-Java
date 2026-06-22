package user.co.jatri.pages.services.launch;

import org.openqa.selenium.WebDriver;
import user.co.jatri.pages.base.BasePage;

public class TripListPage extends BasePage {
    public TripListPage (WebDriver driver){
        super(driver);
        System.out.println("new setup");
    }
}

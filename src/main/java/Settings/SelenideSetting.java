package Settings;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class SelenideSetting {
    static PropertyReader properties = new PropertyReader();
    private static final List<WebDriver> drivers = new ArrayList<>();

    public static void selenideSetting() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = properties.getPropValues("mainUrl");
        Configuration.browserSize = null;

        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--start-maximized");


        Selenide.open(Configuration.baseUrl);

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3ODcxNDMxMDAsImlhdCI6MTc4NzA1NjcwMCwianRpIjoiNjNkMjBiMjMtZjI1ZC00YzU4LWI0MzctZWI3Yjg1Yjk1NDZmIiwic3ViIjoiYTIwOTM1ZGMtYzU5OC00NTY2LWI3ZGItZGYwY2QxY2Y5NDg2In0.iaTxagCWzV-SwKGwqswyDaAYF5bzL5j98aSyJqTnw9k";

        Selenide.executeJavaScript(
                "window.localStorage.setItem('token', arguments[0]);",
                token
        );

        Selenide.open(Configuration.baseUrl);

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));

        drivers.add(WebDriverRunner.getWebDriver());
    }


    public static void closeAllBrowserSessions() {
        for (WebDriver driver : drivers) {
            if (driver != null) {
                driver.quit();
            }
        }
        drivers.clear();
    }
}
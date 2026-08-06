package Settings;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3ODYwMDg3MzEsImp0aSI6IjVkMDRjNDJmLWE0N2UtNGY3ZC05N2RiLWY2M2EyNzA3Zjg0ZiIsInN1YiI6ImEyNmJhMDViLWQ5NTMtNDk5YS05OTE4LTFkMWVkZjBhMTJhZCJ9.IGOjhY51V9hdwbHhKdpwYqu877kW6PuzOI6ZLgtVzxc";

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
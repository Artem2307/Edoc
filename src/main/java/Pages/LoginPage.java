package Pages;//Сторінка входу

import Settings.PropertyReader;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static Pages.UploadDocumentPage.FILE_INPUT;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private static final SelenideElement LOGIN_STRING = $x("//input[@name='login']");
    private static final SelenideElement PASSWORD_STRING = $x("//input[@name='password']");
    public static final SelenideElement PASSWORD_QES_STRING = $x("//div[3]//input[@name='password']");
    private static final SelenideElement SIGN_IN_BUTTON = $x("//button[@type='submit']");
    public static final SelenideElement SIGN_IN_QES_BUTTON = $x("//button[2][@type='submit']");
    private static final SelenideElement QES_BUTTON = $x("//div[text()='Вхід з КЕП']");
    private static final SelenideElement ERROR_MESSAGE_LOGIN = $x("//*[@id='input-error']");

    @Step("Ввести логін {userName} і пароль {password} і увійти в профіль")
    public LoginPage login(String userName,String password){
        sentUserName(userName);
        sentPassword(password);
        clickSingButton();
        return this;
    }

    @Step("Вхід через КЕП")
    public LoginPage loginQES(String fileName,String password){
        QES_BUTTON.should(visible,Duration.ofSeconds(10)).click();
        Selenide.sleep(1000);
        File file = new File("src/main/resources/files/" + fileName);
        FILE_INPUT.uploadFile(file);
        PASSWORD_QES_STRING.should(visible,Duration.ofSeconds(10)).sendKeys(password);
        SIGN_IN_QES_BUTTON.should(visible,Duration.ofSeconds(10)).click();
        return this;
    }
    public LoginPage sentUserName(String userName){
        LOGIN_STRING.should(visible,Duration.ofSeconds(10)).sendKeys(userName);
        return this;
    }

    public LoginPage sentPassword(String password){
        PASSWORD_STRING.should(visible,Duration.ofSeconds(10)).sendKeys(password);
        return this;
    }

    public LoginPage clickSingButton(){
        SIGN_IN_BUTTON.should(visible,Duration.ofSeconds(10)).click();
        return this;
    }
}

package Pages;//Сторінка входу

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private static final SelenideElement LOGIN_STRING = $x("//input[@name='login']");
    private static final SelenideElement PASSWORD_STRING = $x("//input[@name='password']");
    private static final SelenideElement SIGN_IN_BUTTON = $x("//button[@type='submit']");
    private static final SelenideElement ERROR_MESSAGE_LOGIN = $x("//*[@id='input-error']");

    @Step("Ввести логін {userName} і пароль {password} і увійти в профіль")
    public LoginPage login(String userName,String password){
        sentUserName(userName);
        sentPassword(password);
        clickSingButton();
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

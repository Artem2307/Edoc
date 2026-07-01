package Pages;//Сторінка входу

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private static final SelenideElement EMAIL_STRING = $x("");
    private static final SelenideElement PASSWORD_STRING = $x("//*[@id='password']");
    private static final SelenideElement SIGN_IN_BUTTON = $x("//*[@id='kc-login']");
    private static final SelenideElement ERROR_MESSAGE_LOGIN = $x("//*[@id='input-error']");
    private static final SelenideElement ONE_TIME_CODE_STRING = $x("//*[@id='otp']");
    private static final SelenideElement ERROR_OTP = $x("//*[@id='input-error-otp-code']");
    private static final ElementsCollection ACCOUNTS_USERS = $$x("//*[@id='kc-otp-login-form']//span//span[@class='pf-c-tile__title']");

    @Step("Ввести логін")
    public LoginPage sentUserName(String userName){
        EMAIL_STRING.should(visible,Duration.ofSeconds(10)).sendKeys(userName);
        return this;
    }
}

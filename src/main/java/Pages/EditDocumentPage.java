package Pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class EditDocumentPage {
    private static final SelenideElement DELETE_BUTTON = $x("//span[@aria-label='Видалити документ']/button");
    private static final SelenideElement DELETE_BUTTON_IN_POP_UP = $x("//button[text()='Видалити']");
    private static final SelenideElement SING_DOCUMENT_BUTTON = $x(" //*[@id='bar']");

    @Step("Видалити документ")
    public EditDocumentPage deleteDocument(){
        DELETE_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        DELETE_BUTTON_IN_POP_UP.should(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Натисунути підписати документ")
    public EditDocumentPage SignDocumentButtonClick(){
        SING_DOCUMENT_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        return this;
    }
}

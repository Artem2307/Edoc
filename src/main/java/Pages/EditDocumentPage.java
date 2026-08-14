package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class EditDocumentPage {
    private static final SelenideElement DELETE_BUTTON = $x("//span[@aria-label='Видалити документ']/button");
    private static final SelenideElement DELETE_BUTTON_IN_POP_UP = $x("//button[text()='Видалити']");
    private static final SelenideElement SING_DOCUMENT_BUTTON = $x(" //*[@id='bar']");
    private static final SelenideElement ARCHIVE_BUTTON = $x("//main/section/div[1]/div[1]/span[1]/button");
    private static final SelenideElement ARCHIVE_POP_UP_BUTTON = $x("//button[text()='Архівувати']");

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

    @Step("Натиснути кнопку архівувати")
    public EditDocumentPage arсhiveButtonClick(){
        ARCHIVE_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        ARCHIVE_POP_UP_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        return this;
    }
}

package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class UploadDocumentPage {
    private static final SelenideElement TYPE_DOCUMENT_BUTTON = $x("//button[@aria-label='Open']");
    private static final ElementsCollection TYPES_COLLECTIONS = $$x("//button//span");
    public static final SelenideElement FILE_INPUT = $x("//div[2]//div[@role='presentation']//input[@type='file']");
    private static final SelenideElement SAVE_BUTTON = $x("//button[text()='Зберегти']");

    @Step("Завантажити документ с такими параметрами як Тип документу: {type} і файл {fileName}")
    public UploadDocumentPage uploadDocument(String type,String fileName){
        TYPE_DOCUMENT_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        TYPES_COLLECTIONS.filter(text(type)).first().should(visible, Duration.ofSeconds(10)).click();

        File file = new File("src/main/resources/files/" + fileName);

        FILE_INPUT.uploadFile(file);

        return this;
    }

    @Step("Натиснути кнопку зберегти")
    public UploadDocumentPage clickSaveButton(){
        SAVE_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        return this;
    }
}

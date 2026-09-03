package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage {
    private static final SelenideElement UPLOAD_DOCUMENT_BUTTON = $x("//button/span[text()='Завантажити'] ");
    private static final ElementsCollection SECTION_COLLECTION = $$x("//span[@style='opacity: 1;']");


    @Step("Клікнути на кнопку завантажити")
    public HomePage clickUploadButton(){
        UPLOAD_DOCUMENT_BUTTON.should(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Відкрити розділ у меня {0}")
    public HomePage selectSection(String name){
        SECTION_COLLECTION.filter(Condition.text(name)).first().click();
        return this;
    }

}

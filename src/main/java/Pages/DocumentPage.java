package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;

public class DocumentPage {
    private static final ElementsCollection DOCUMENTS_COLLECTION = $$x("//div[@role='gridcell']");

    @Step("Відкрити документ {0}")
    public DocumentPage selectDocument(String name){
        DOCUMENTS_COLLECTION.filter(Condition.text(name)).first().click();
        return this;
    }
}

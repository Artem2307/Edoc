package Pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;

public class DocumentsCollectionPage {
    private static final ElementsCollection DOCUMENTS_COLLECTION = $$x("//p");

    @Step("Відкрити документ {name}")
    public DocumentsCollectionPage clickDocument(String name){
        DOCUMENTS_COLLECTION.filter(text(name)).first().should(visible,Duration.ofSeconds(10)).click();
        return this;
    }
}

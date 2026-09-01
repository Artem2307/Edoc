package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class DocumentsCollectionPage {
    private static final ElementsCollection DOCUMENTS_COLLECTION = $$x("//p");
    private static final ElementsCollection DOCUMENTS_NUMBER_COLLECTION = $$x("//p");
    private static final SelenideElement SEARCH_STRING = $x("//input[@placeholder=\"Пошук за документом, компанією, контрагентом..\"]");

    @Step("Пошук документа {0}")
    public DocumentsCollectionPage searchDocument(String name){
        SEARCH_STRING.should(visible,Duration.ofSeconds(10)).clear();
        SEARCH_STRING.sendKeys(name);
        return this;
    }

    @Step("Відкрити документ {name}")
    public DocumentsCollectionPage clickDocument(String name){
        DOCUMENTS_COLLECTION.filter(text(name)).first().should(visible,Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Відкрити документ {name}")
    public DocumentsCollectionPage clickDocumentNumber(String name){
        DOCUMENTS_NUMBER_COLLECTION.filter(text(name)).first().should(visible,Duration.ofSeconds(10)).click();
        return this;
    }
}

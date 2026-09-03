package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static Pages.RequiredAttributesPage.SAVE_BUTTON;
import static com.codeborne.selenide.Selenide.$x;

public class EditDocumentInformsPage {
    public static final SelenideElement NUMBER_DOCUMENT = $x("//div[3]/div[2]/div/input");
    public static final SelenideElement NUMBER_OPTIONAL_ATTRIBUTE  = $x("//div/input[@type=\"number\"]");

    public EditDocumentInformsPage sendNumberDocument(String number){
        NUMBER_DOCUMENT.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(number);
        NUMBER_OPTIONAL_ATTRIBUTE.should(Condition.visible, Duration.ofSeconds(10)).sendKeys(number);
        SAVE_BUTTON.should(Condition.visible, Duration.ofSeconds(10)).click();
        return this;
    }
}

package Pages;

import Settings.PropertyReader;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static Pages.LoginPage.PASSWORD_QES_STRING;
import static Pages.LoginPage.SIGN_IN_QES_BUTTON;
import static Pages.UploadDocumentPage.FILE_INPUT;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;

public class SignDocumentPage {
    private static final ElementsCollection SIGNING_OPTIONS = $$x("//div[@class=\"laxNPSa7cc2fof3JGP8e\"]//div//p");

    @Step("Підписати документ {0}")
    public SignDocumentPage selectSigningOptions(String options,String fileName){
        SIGNING_OPTIONS.filter(Condition.text(options)).first().should(visible, Duration.ofSeconds(10)).click();

        File file = new File("src/main/resources/files/" + fileName);

        FILE_INPUT.uploadFile(file);

        PropertyReader propertyReader = new PropertyReader();
        PASSWORD_QES_STRING.should(visible,Duration.ofSeconds(10)).sendKeys(propertyReader.getPropValues("password"));
        SIGN_IN_QES_BUTTON.should(visible,Duration.ofSeconds(10)).click();
        return this;
    }
}

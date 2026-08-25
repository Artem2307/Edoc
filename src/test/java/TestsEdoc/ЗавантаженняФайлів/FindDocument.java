package TestsEdoc.ЗавантаженняФайлів;

import TestsEdoc.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FindDocument extends BaseTest {
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Сценарій 9: Пошук документа по параметрам")
    public void signDocumentError() {
        accountSelectionPage
                .selectAccount("3672906277");

        homePage
                .selectSection("Всі документи");

        documentsCollectionPage
                .searchDocument("4234")
                .clickDocumentNumber("4234");

        homePage
                .selectSection("Всі документи");

        documentsCollectionPage
                .searchDocument("3412")
                .clickDocumentNumber("3412");

        homePage
                .selectSection("Всі документи");

        documentsCollectionPage
                .searchDocument("test123")
                .clickDocument("test123");

    }
}

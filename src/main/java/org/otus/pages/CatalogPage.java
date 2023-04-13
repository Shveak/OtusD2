package org.otus.pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.support.GuiceScoped;

public class CatalogPage extends BasePageAbs {

    @FindBy(xpath = "//h5[contains(text(), 'также может быть интересно')]")
    private WebElement message;

    @FindBy(xpath = "//button[contains(text(), 'Показать еще')]")
    private WebElement buttonMore;

    @Inject
    public CatalogPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public void openAllCourses() {
        while (exists(buttonMore, 2)) {
            message.click();
            buttonMore.click();
        }
        message.click();
    }
}

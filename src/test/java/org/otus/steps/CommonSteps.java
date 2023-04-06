package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.otus.driver.WebDriverFactory;
import org.otus.pages.MainPage;
import org.otus.support.GuiceScoped;

public class CommonSteps {
    @Inject
    private GuiceScoped guiceScoped;
    @Inject
    private MainPage mainPage;

    @Пусть("^Я открываю главную старницу (.*) в браузере (.*)$")
    public void openMainPage(String url, String browser) {
        guiceScoped.driver = new WebDriverFactory().getDriver(browser);
        mainPage.open(url);
    }
}

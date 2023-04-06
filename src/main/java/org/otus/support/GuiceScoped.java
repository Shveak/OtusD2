package org.otus.support;

import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import org.otus.driver.WebDriverFactory;

@ScenarioScoped
public class GuiceScoped {

    public WebDriver driver;
}

package org.otus.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import org.otus.support.GuiceScoped;

public class Hooks {
    @Inject
    private GuiceScoped guiceScoped;

    @After
    public void after() {
        if (guiceScoped.driver != null) {
            guiceScoped.driver.close();
            guiceScoped.driver.quit();
        }
    }
}

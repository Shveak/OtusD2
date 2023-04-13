package org.otus.objects;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.otus.support.GuiceScoped;
import org.otus.waiters.Waiter;

public abstract class PageObjectAbs {

    protected Waiter waiter;
    @Inject
    protected GuiceScoped guiceScoped;

    @Inject
    public PageObjectAbs(GuiceScoped guiceScoped) {
        this.guiceScoped = guiceScoped;
        PageFactory.initElements(this.guiceScoped.driver, this);
    }

    public boolean exists(WebElement webElement, int second) {
        long startTime = System.currentTimeMillis();
        do {
            try {
                webElement.getText();
                return true;
            } catch (Exception e) {
                if (!(e.getMessage().startsWith("no such element:"))) {
                    e.printStackTrace();
                }
            }
        } while ((System.currentTimeMillis() - startTime) / 1000 <= second);
        return false;
    }
}

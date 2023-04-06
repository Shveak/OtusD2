package org.otus.objects;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
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
}

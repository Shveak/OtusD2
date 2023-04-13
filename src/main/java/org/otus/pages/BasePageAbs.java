package org.otus.pages;

import com.google.inject.Inject;
import org.otus.objects.PageObjectAbs;
import org.otus.support.GuiceScoped;

public abstract class BasePageAbs extends PageObjectAbs {

    @Inject
    public BasePageAbs(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public void open(String url) {
        guiceScoped.driver.get(url);
    }

    public String getTitle() {
        System.out.printf("Заголовок текущей страницы %s%n", guiceScoped.driver.getTitle());
        return guiceScoped.driver.getTitle();
    }
}

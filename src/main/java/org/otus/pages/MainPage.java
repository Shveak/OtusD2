package org.otus.pages;

import com.google.inject.Inject;
import org.otus.support.GuiceScoped;

public class MainPage extends BasePageAbs {

    @Inject
    public MainPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

}
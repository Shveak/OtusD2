package org.otus.pages;

import com.google.inject.Inject;
import org.otus.support.GuiceScoped;

public class CatalogPage extends BasePageAbs {
    @Inject
    public CatalogPage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

}

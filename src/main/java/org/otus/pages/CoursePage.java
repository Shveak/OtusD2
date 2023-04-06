package org.otus.pages;

import com.google.inject.Inject;
import org.otus.support.GuiceScoped;

public class CoursePage extends BasePageAbs {
    @Inject
    public CoursePage(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }
}

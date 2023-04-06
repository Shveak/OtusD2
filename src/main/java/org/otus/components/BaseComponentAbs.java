package org.otus.components;

import com.google.inject.Inject;
import org.otus.objects.PageObjectAbs;
import org.otus.support.GuiceScoped;

public abstract class BaseComponentAbs extends PageObjectAbs {

    @Inject
    public BaseComponentAbs(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }
}

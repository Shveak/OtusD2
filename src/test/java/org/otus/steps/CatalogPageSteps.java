package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Если;
import org.otus.pages.CatalogPage;

public class CatalogPageSteps {

    @Inject
    private CatalogPage catalogPage;

    @Если("^Показываю все курсы на странице$")
    public void showingAllCourses() {
        catalogPage.openAllCourses();
    }
}

package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Если;
import org.otus.components.CatalogCourses;

public class CatalogCoursesSteps {

    @Inject
    private CatalogCourses catalogCourses;

    @Если("^Выбрать подготовительный курс, у которого стоимость (.*)$")
    public void selectCourseWithCost(String cost) {
        catalogCourses.selectCourseBycCost(cost);
    }
}

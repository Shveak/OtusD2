package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import org.otus.components.BannersCourses;
import org.otus.pages.CoursePage;

public class CoursesPageSteps {

    @Inject
    private CoursePage coursePage;

    @Тогда("^Откроется страница курса с залоговком содержащим (.*)$")
    public void openingPageWithTitle(String title) {
        Assertions.assertTrue(coursePage.getTitle().contains(title) || coursePage.getTitle().contains("otus.ru/lessons"), "Открыта страница не соответствует заданной");
    }
}

package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import org.otus.pages.CoursePage;

public class CoursesPageSteps {

    @Inject
    private CoursePage coursePage;

    @Тогда("^Откроется страница курса с залоговком содержащим (.*)$")
    public void openingPageWithTitle(String title) {
        Assertions.assertTrue(coursePage.getTitle().contains(title), "Открыта страница не соответствует заданной");
    }
}

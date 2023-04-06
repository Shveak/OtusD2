package org.otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.ru.Если;
import org.otus.components.BannersCourses;

public class BunnersCoursesSteps {

    @Inject
    private BannersCourses bannersCourses;

    @Если("^Кликнуть на баннер курса с названием (.*)$")
    public void clickOnBannerCourseWithTitle(String title) {
        bannersCourses.selectCourseByTitle(title).click();
    }

    @Если("^Выбрать курсы стартующие с (\\d{2}\\.\\d{2}\\.[0-9]{4})$")
    public void selectBannerCourseWithDateBeginAfter(String dateStart) {
        bannersCourses.selectCourseWithDateStartAfter(dateStart);
    }
}

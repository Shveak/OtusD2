package org.otus.components;

import com.google.inject.Inject;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.data.Month;
import org.otus.pages.CoursePage;
import org.otus.support.GuiceScoped;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BannersCourses extends BaseComponentAbs {
    private WebElement currentBunner;
    @FindBy(xpath = "//a[@href and contains(@class, 'lessons')]")
    private List<WebElement> listBanner;

    @Inject
    public BannersCourses(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public BannersCourses selectCourseByTitle(String title) {
        System.out.println("----------------------------------------------------------------------");
        List<WebElement> listBanners = listBanner.stream()
                .filter(x -> getTitle(x).contains(title))
                .collect(Collectors.toList());
        Assertions.assertFalse(listBanner.isEmpty(), String.format("Не найдены баннеры с названием курса %s", title));
        int numberCourse = 0;
        if (listBanners.size() > 1) {
            numberCourse = RandomUtils.nextInt(0, listBanners.size());
        }
        currentBunner = listBanners.get(numberCourse);
        System.out.printf("Выбран баннер с названием курса %s, с датой начала %s%n", getTitleCurrentBanner(), getDateBeginCurrentBanner());
        System.out.println("----------------------------------------------------------------------");
        return this;
    }

    public void selectCourseWithDateStartAfter(String dateStart) {
        System.out.println("----------------------------------------------------------------------");
        LocalDate localDate = LocalDate.parse(dateStart, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Map<String, LocalDate> map = listBanner.stream()
                .filter(x -> getDateBegin(x) != null)
                .filter(x -> !getDateBegin(x).isBefore(localDate))
                .collect(Collectors.toMap(this::getTitle, this::getDateBegin));
        Assertions.assertFalse(listBanner.isEmpty(), String.format("Не найдены баннеры с началом курса после %s", dateStart));
        System.out.printf("Список курсов, с датой начала больше %s%n", dateStart);
        map.forEach((key, value) -> System.out.printf("Курс %s, дата начала %s%n", key, value.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        System.out.println("----------------------------------------------------------------------");
    }

    public BannersCourses selectCourseWithMaxDateBegin() {
        currentBunner = listBanner.stream()
                .filter(x -> getDateBegin(x) != null)
                .reduce((p1, p2) -> {
                    LocalDate d1 = getDateBegin(p1);
                    LocalDate d2 = getDateBegin(p2);
                    return d1.isAfter(d2) || d1.isEqual(d2) ? p1 : p2;
                })
                .orElseThrow(() -> new AssertionError("Не удалось вычислить мах значение"));
        return this;
    }

    public BannersCourses selectCourseWithMinDateBegin() {
        currentBunner = listBanner.stream()
                .filter(x -> getDateBegin(x) != null)
                .reduce((p1, p2) -> {
                    LocalDate d1 = getDateBegin(p1);
                    LocalDate d2 = getDateBegin(p2);
                    return d1.isBefore(d2) ? p1 : p2;
                })
                .orElseThrow(() -> new AssertionError("Не удалось вычислить min значение"));
        return this;
    }

    public String getTitleCurrentBanner() {
        return getTitle(getCurrentBanner());
    }

    public String getDateBeginCurrentBanner() {
        return getDateBegin(getCurrentBanner()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public CoursePage click() {
        getCurrentBanner().click();
        return new CoursePage(guiceScoped);
    }

    public String getAttribute(String str) {
        return getCurrentBanner().getAttribute(str);
    }

    public List<String> getAllBunnersTitles() {
        return listBanner.stream().map(this::getTitle).collect(Collectors.toList());
    }

    private WebElement getCurrentBanner() {
        return currentBunner == null ? listBanner.get(0) : currentBunner;
    }

    private String getTitle(WebElement webElement) {
        String titleLocator = ".//div[contains(@class, 'title')]";
        List<WebElement> allElements = webElement.findElements(By.xpath(titleLocator));
        if (allElements != null && !allElements.isEmpty()) {
            return allElements.get(0).getText();
        } else {
            throw new NoSuchElementException("Элемент в баннере не найден " + titleLocator);
        }
    }

    private LocalDate getDateBegin(WebElement webElement) {
        List<String> dateBlockLocatorList = Arrays.asList(".//div[contains(@class, 'new-item-bottom_spec')]/div[2]",
                ".//div[contains(@class, 'start')]");
        List<WebElement> allElements = null;
        for (String locator : dateBlockLocatorList) {
            allElements = webElement.findElements(By.xpath(locator));
            if (allElements != null && !allElements.isEmpty()) {
                break;
            }
        }
        if (allElements == null || allElements.isEmpty()) {
            throw new NoSuchElementException("Элемент в баннере не найден " + dateBlockLocatorList);
        }
        String strDate = getDate(allElements.get(0).getText());
        if (strDate.isEmpty()) {
            return null;
        }
        List<String> pathDate = Arrays.asList(strDate.split(" "));
        return LocalDate.parse(String.format("%1$2s", pathDate.get(0)).replace(' ', '0') + "."
                + String.format("%1$2s", Month.of(pathDate.get(1)).getNumber()).replace(' ', '0')
                + "." + pathDate.get(2), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private String getDate(String str) {
        Pattern pattern = Pattern.compile("^\\D+$");
        Matcher matcher = pattern.matcher(str);
        str = matcher.find() ? "" : str;
        if (str.isEmpty()) {
            return str;
        }
        pattern = Pattern.compile(" \\d+ месяц");
        matcher = pattern.matcher(str);
        while (matcher.find()) {
            str = str.substring(0, matcher.start());
        }
        pattern = Pattern.compile("^[СВ] ");
        matcher = pattern.matcher(str);
        while (matcher.find()) {
            str = str.substring(2);
        }
        pattern = Pattern.compile("^(\\D+) ([0-9]{4}) года");
        matcher = pattern.matcher(str);
        str = matcher.find() ? "1 " + matcher.group(1) + " " + matcher.group(2) : str + " " + LocalDate.now().getYear();
        return str;
    }
}

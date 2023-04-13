package org.otus.components;

import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.support.GuiceScoped;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalogCourses extends BaseComponentAbs {
    @FindBy(xpath = "//section/div/div/a")
    private List<WebElement> listBanner;

    @Inject
    public CatalogCourses(GuiceScoped guiceScoped) {
        super(guiceScoped);
    }

    public void selectCourseBycCost(String cost) {
        System.out.println("----------------------------------------------------------------------");
        Map<String, Integer> map = listBanner.stream().collect(Collectors.toMap(this::getTitle, this::getCost));
        Map.Entry pair = null;
        switch (cost) {
            case "максимальная": {
                pair = map.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow(() -> new AssertionError("Не удалось вычислить максимум стоимости обучения"));
                break;
            }
            case "минимальная": {
                pair = map.entrySet()
                        .stream()
                        .filter(x -> x.getValue() > 0)
                        .min(Map.Entry.comparingByValue())
                        .orElseThrow(() -> new AssertionError("Не удалось вычислить минимум стоимости обучения"));
                break;
            }
            default:
                throw new AssertionError(String.format("Не заданы действия для стоимости '%s'", cost));
        }
        System.out.printf("Курс со стоимостью '%s': %s, стоимость %s%n", cost, pair.getKey(), pair.getValue());
        System.out.printf("Курсы, у которых стоимость, или отсутствует, или указанна в рассрочку: %s%n",
                map.entrySet()
                        .stream()
                        .filter(x -> x.getValue() == 0)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList())
        );
        System.out.println("----------------------------------------------------------------------");
    }

    private String getTitle(WebElement webElement) {
        String titleLocator = ".//h6/div";
        if (!(exists(webElement, 1))) {
            System.out.println("--  не пойму почему такое происходит, сначала плитка курса есть, а пока идет чтение других курсов она пропадает ----- ");
        }
        List<WebElement> allElements = webElement.findElements(By.xpath(titleLocator));
        if (allElements != null && !allElements.isEmpty()) {
            return allElements.get(0).getText();
        } else {
            throw new NoSuchElementException("Элемент в блоке курса не найден " + titleLocator);
        }
    }

    private Integer getCost(WebElement webElement) {
        String url = webElement.getAttribute("href");
        if (url.isEmpty()) {
            return 0;
        }
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = document.select("nobr");
        if (elements.size() == 0) {
            elements = document.selectXpath("//div[p[contains(text(), 'Стоимость')]]/div/div");
            if (elements.size() != 1) {
                elements = document.selectXpath("(//div[text() = 'Стоимость обучения']/ancestor::div/div/div[contains(text(), '₽')])[1]");
                if (elements.size() == 0) {
                    return 0;
                }
            }
        }
        if (elements.text().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(elements.text().replace("₽", "").replace(" ", ""));
    }
}

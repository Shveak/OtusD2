package org.otus.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverFactory {

    public WebDriver getDriver(String browser) {
        switch (browser) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            }
            case "opera": {
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            }
            default:
                throw new Error(String.format("Отсутствует драйвер для браузера '%s'", browser));
        }
    }
}

package org.otus.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;

public class WebDriverFactory {

    public WebDriver getDriver(String browser) {
        switch (browser) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--homepage=about:blank");
                options.addArguments("--ignore-certificate-errors");
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                options.setCapability("enableVNC", Boolean.parseBoolean(System.getProperty("enableVNC", "false")));
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(options);
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

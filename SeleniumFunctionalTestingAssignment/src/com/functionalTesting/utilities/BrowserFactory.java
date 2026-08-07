package com.functionalTesting.utilities;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {

    protected static WebDriver driver;

    public static WebDriver startBrowser(
            String browserName,
            String url) {

        if (browserName.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();

            // Block location permission
            prefs.put(
                    "profile.default_content_setting_values.geolocation",
                    2
            );

            options.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("IE")) {

            driver = new InternetExplorerDriver();

        } else {

            throw new IllegalArgumentException(
                    "Invalid browser: " + browserName
            );
        }

        driver.manage().window().maximize();

        driver.get(url);

        return driver;
    }


    public static void tearDown() {

        if (driver != null) {

            driver.quit();

            driver = null;
        }
    }
}
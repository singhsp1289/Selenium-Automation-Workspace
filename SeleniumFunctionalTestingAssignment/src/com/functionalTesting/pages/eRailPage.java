package com.functionalTesting.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class eRailPage {

    WebDriver driver;

    @FindBy(id = "txtStationFrom")
    WebElement fromTextbox;

    @FindBy(xpath = "//div[contains(@class,'autocomplete')]/div[@title]")
    List<WebElement> stations;

    @FindBy(xpath = "//*[@id='tdDateFromTo']/input")
    WebElement dateBox;


    public eRailPage(WebDriver ldriver) {

        this.driver = ldriver;

        PageFactory.initElements(driver, this);
    }

    public void typeSourceStation(String stn) {

        fromTextbox.click();

        fromTextbox.clear();

        fromTextbox.sendKeys(stn);
    }

    public List<String> getStations() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        By.xpath("//div[contains(@class,'autocomplete')]/div[@title]"),
                        3
                )
        );

        List<WebElement> stationElements =
                driver.findElements(
                        By.xpath("//div[contains(@class,'autocomplete')]/div[@title]")
                );

        List<String> stationList =
                new ArrayList<>();

        System.out.println("===== Station List =====");

        for (WebElement ele : stationElements) {

            String stationName =
                    ele.getAttribute("title").trim();

            stationList.add(stationName);

            System.out.println(stationName);
        }

        System.out.println("========================");

        return stationList;
    }

    public String selectFourthStation() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        By stationLocator =
                By.xpath("//div[contains(@class,'autocomplete')]/div[@title]");

        wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(
                        stationLocator,
                        3
                )
        );

        List<WebElement> stationList =
                driver.findElements(stationLocator);

        String fourthStation =
                stationList.get(3)
                           .getAttribute("title")
                           .trim();

        System.out.println(
                "Fourth Station Selected: " + fourthStation
        );

        stationList.get(3).click();

        return fourthStation;
    }

    public void selectDateAfterDays(int days) {

        LocalDate futureDate =
                LocalDate.now().plusDays(days);

        String targetMonth =
                futureDate.format(
                        DateTimeFormatter.ofPattern(
                                "MMM-yy",
                                Locale.ENGLISH
                        )
                );

        String targetDay =
                String.valueOf(
                        futureDate.getDayOfMonth()
                );

        System.out.println(
                "Target Date: " + futureDate
        );

        System.out.println(
                "Target Month: " + targetMonth
        );

        System.out.println(
                "Target Day: " + targetDay
        );

        dateBox.click();


        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("divCalender")
                )
        );

        List<WebElement> monthTables =
                driver.findElements(
                        By.xpath("//table[@class='Month']")
                );


        boolean dateSelected = false;

        for (WebElement table : monthTables) {

            String currentMonth =
                    table.findElement(
                            By.xpath(".//tr[1]/td")
                    ).getText().trim();


            System.out.println(
                    "Calendar Month: " + currentMonth
            );

            if (currentMonth.equals(targetMonth)) {

                WebElement targetDate =
                        table.findElement(
                                By.xpath(
                                    ".//td[@onclick and normalize-space()='"
                                    + targetDay +
                                    "']"
                                )
                        );

                targetDate.click();

                dateSelected = true;

                System.out.println(
                        "Date Selected: "
                        + futureDate
                );

                break;
            }
        } 
        
        if (!dateSelected) {

            throw new RuntimeException(
                    "Unable to find date "
                    + futureDate
                    + " in calendar."
            );
        }
    }
}
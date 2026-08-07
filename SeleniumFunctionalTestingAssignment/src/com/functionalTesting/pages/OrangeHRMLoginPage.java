package com.functionalTesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrangeHRMLoginPage {

    WebDriver driver;

    public OrangeHRMLoginPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);

    }

    @FindBy(name = "username")
    WebElement username;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;

    @FindBy(xpath = "//h6[text()='Dashboard']")
    WebElement dashboard;

    @FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
    WebElement errorMessage;

    public void enterUsername(String userName) {

        username.clear();

        username.sendKeys(userName);

    }

    public void enterPassword(String passWord) {

        password.clear();

        password.sendKeys(passWord);

    }

    public void clickLogin() {

        loginButton.click();

    }

    public boolean isDashboardDisplayed() {

        return dashboard.isDisplayed();

    }

    public String getErrorMessage() {

        return errorMessage.getText();

    }

}
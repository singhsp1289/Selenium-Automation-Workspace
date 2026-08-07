package com.functionalTesting.testcases;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.functionalTesting.pages.OrangeHRMLoginPage;
import com.functionalTesting.utilities.BrowserFactory;
import com.functionalTesting.utilities.ExcelUtil;
import com.functionalTesting.utilities.ExtentListener;

public class OrangeHRMLoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {

        driver = BrowserFactory.startBrowser(
                "chrome",
                "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"
        );

        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {

        String filePath =
                System.getProperty("user.dir")
                + "/testdata/OrangeHRMLoginData.xlsx";

        return ExcelUtil.getExcelData(
                filePath,
                "LoginData"
        );
    }

    @Test(dataProvider = "loginData")
    public void verifyLogin(
            String username,
            String password,
            String expected) {


        ExtentTest test =
                ExtentListener.getTest();


        try {

            test.pass(
                    "Step 1: OrangeHRM login page opened successfully"
            );


            OrangeHRMLoginPage loginPage =
                    new OrangeHRMLoginPage(driver);


            loginPage.enterUsername(username);


            test.pass(
                    "Step 2: Username entered successfully: "
                    + username
            );

            loginPage.enterPassword(password);


            test.pass(
                    "Step 3: Password entered successfully: "
                    + password
            );

            loginPage.clickLogin();


            test.pass(
                    "Step 4: Login button clicked successfully"
            );

            if (expected.equalsIgnoreCase("Valid")) {


                Assert.assertTrue(
                        loginPage.isDashboardDisplayed(),
                        "Dashboard is not displayed after valid login"
                );


                test.pass(
                        "Step 5: Valid login successful - Dashboard displayed"
                );


                System.out.println(
                        "Valid Login Passed : "
                        + username
                );


            } else {


                String errorMessage =
                        loginPage.getErrorMessage();


                test.pass(
                        "Step 5: Invalid login error message displayed: "
                        + errorMessage
                );


                Assert.assertTrue(
                        errorMessage.contains("Invalid"),
                        "Invalid login message not displayed"
                );


                test.pass(
                        "Step 6: Invalid login validation passed"
                );


                System.out.println(
                        "Invalid Login Passed : "
                        + username
                );
            }

            test.pass(
                    "Test execution completed successfully"
            );


        } catch (Exception e) {


            test.fail(
                    "Test execution failed: "
                    + e.getMessage()
            );


            throw e;
        }
    }

    @AfterMethod
    public void closeBrowser() {

        BrowserFactory.tearDown();

    }
}
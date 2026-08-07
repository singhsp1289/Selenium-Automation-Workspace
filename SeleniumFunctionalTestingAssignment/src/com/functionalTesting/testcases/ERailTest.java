package com.functionalTesting.testcases;

import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.functionalTesting.pages.eRailPage;
import com.functionalTesting.utilities.BrowserFactory;
import com.functionalTesting.utilities.ExcelUtil;
import com.functionalTesting.utilities.ExtentListener;

public class ERailTest extends BrowserFactory {

    @Test
    public void verifyValidTrains() throws Exception {

        ExtentTest test =
                ExtentListener.getTest();

        try {

            startBrowser(
                    "chrome",
                    "https://erail.in/"
            );


            test.pass(
                    "Step 1: eRail website opened successfully"
            );

            eRailPage homePage = new eRailPage(driver);
            homePage.typeSourceStation("DEL");


            test.pass(
                    "Step 2-4: From field cleared and DEL entered successfully"
            );

            List<String> actualStations =
                    homePage.getStations();


            if (actualStations.isEmpty()) {

                test.fail(
                        "Station dropdown is empty"
                );

                Assert.fail(
                        "Station dropdown is empty"
                );
            }

            test.pass("Station dropdown retrieved successfully. Total stations: "
                    + actualStations.size()
            );

            System.out.println("========== ACTUAL STATION LIST ==========");

            for (int i = 0;
                 i < actualStations.size();
                 i++) {

                System.out.println(
                        (i + 1)
                        + ". "
                        + actualStations.get(i)
                );
            }


            System.out.println("========================================="
            );

            List<String> expectedStations =
                    Arrays.asList(
                            "Denduluru",
                            "Delang",
                            "Delhi",
                            "Delhi Azadpur",
                            "Delhi Cantt"
                    );

            String expectedFile =
                    System.getProperty("user.dir")
                    + "/testdata/ExpectedStations.xlsx";

            ExcelUtil.createExpectedExcel(
                    expectedFile,
                    expectedStations
            );

            test.pass(
                    "Step 6: Expected station Excel created successfully"
            );

            String actualFile =
                    System.getProperty("user.dir")
                    + "/testdata/ActualStations.xlsx";


            ExcelUtil.writeActualExcel(
                    actualFile,
                    actualStations
            );

            test.pass(
                    "Step 7: Actual dropdown station Excel created successfully"
            );

            List<String> expectedFromExcel =
                    ExcelUtil.readStations(
                            expectedFile
                    );

            boolean comparisonResult =
                    true;


            for (String expected :
                    expectedFromExcel) {

                if (actualStations.contains(expected)) {

                    System.out.println(
                            "PASS: " + expected
                    );

                } else {

                    System.out.println(
                            "FAIL: " + expected
                    );

                    comparisonResult =
                            false;
                }
            }

            if (comparisonResult) {

                test.pass(
                        "Step 7: Expected and Actual station lists matched successfully"
                );

            } else {

                test.fail(
                        "Step 7: Expected and Actual station list comparison failed"
                );
            }

            Assert.assertTrue(
                    comparisonResult,
                    "Expected station(s) not found in dropdown."
            );

            String fourthStation =
                    homePage.selectFourthStation();


            System.out.println(
                    "4th Station Selected: "
                    + fourthStation
            );


            test.pass(
                    "Step 5: 4th station selected successfully: "
                    + fourthStation
            );

            homePage.selectDateAfterDays(30);


            test.pass(
                    "Step 8: Journey date selected successfully (Today + 30 days)"
            );

            test.pass(
                    "Step 9: Test execution completed successfully"
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
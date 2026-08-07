package com.functionalTesting.utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;

public class ExtentListener implements ITestListener {

    private static ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();


    public static ExtentTest getTest() {

        return extentTest.get();

    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
                result.getMethod().getMethodName();


        Object[] parameters =
                result.getParameters();

        if (parameters != null &&
                parameters.length >= 3) {

            String username =
                    String.valueOf(parameters[0]);

            String expected =
                    String.valueOf(parameters[2]);

            testName =
                    testName
                    + " - "
                    + username
                    + " - "
                    + expected;
        }

        ExtentTest test =
                ExtentReportManager
                .getReportInstance()
                .createTest(testName);


        extentTest.set(test);
    }


    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTest test = getTest();

        if (test != null) {

            test.pass("Test Passed");

        }
    }


    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test = getTest();

        if (test != null) {

            test.fail(result.getThrowable());

        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest test = getTest();

        if (test != null) {

            test.skip("Test Skipped");

        }
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentReportManager
                .getReportInstance()
                .flush();

        extentTest.remove();
    }

}
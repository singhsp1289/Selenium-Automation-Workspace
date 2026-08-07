package com.functionalTesting.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {

        if (extent == null) {


            String reportPath =
                    System.getProperty("user.dir")
                    + "/test-output/ExtentReport.html";


            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config()
                    .setDocumentTitle(
                            "Functional Testing Automation Report"
                    );

            sparkReporter.config()
                    .setReportName(
                            "Selenium Automation Execution Report"
                    );

            extent = new ExtentReports();
            
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Project",
                    "Selenium Functional Testing Assignment"
            );


            extent.setSystemInfo(
                    "Tester",
                    "Saurabh Pratap Singh"
            );

            extent.setSystemInfo(
                    "Browser",
                    "Chrome"
            );

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG + Maven + POM"
            );

        }


        return extent;

    }


}
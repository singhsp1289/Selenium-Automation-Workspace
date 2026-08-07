package com.functionalTesting.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {


    public static void createExpectedExcel(
            String filePath,
            List<String> expectedStations) throws IOException {


        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("ExpectedStations");


        Row header = sheet.createRow(0);

        header.createCell(0)
              .setCellValue("Expected Station");


        for (int i = 0; i < expectedStations.size(); i++) {

            Row row = sheet.createRow(i + 1);

            row.createCell(0)
               .setCellValue(expectedStations.get(i));

        }

        sheet.autoSizeColumn(0);

        FileOutputStream outputStream =
                new FileOutputStream(filePath);

        workbook.write(outputStream);
        outputStream.close();
        workbook.close();

    }

    public static void writeActualExcel(
            String filePath,
            List<String> actualStations) throws IOException {


        Workbook workbook = new XSSFWorkbook();


        Sheet sheet =
                workbook.createSheet("ActualStations");


        Row header = sheet.createRow(0);

        header.createCell(0)
              .setCellValue("Actual Station");

        for (int i = 0; i < actualStations.size(); i++) {


            Row row = sheet.createRow(i + 1);


            row.createCell(0)
               .setCellValue(actualStations.get(i));

        }

        sheet.autoSizeColumn(0);


        FileOutputStream outputStream =
                new FileOutputStream(filePath);

        workbook.write(outputStream);

        outputStream.close();

        workbook.close();

    }

    public static List<String> readStations(
            String filePath) throws IOException {


        List<String> stations =
                new ArrayList<>();


        FileInputStream inputStream =
                new FileInputStream(filePath);


        Workbook workbook =
                new XSSFWorkbook(inputStream);


        Sheet sheet =
                workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if(row != null &&
               row.getCell(0) != null) {
                stations.add(
                    row.getCell(0)
                    .getStringCellValue()
                    .trim()
                );

            }

        }

        workbook.close();
        inputStream.close();
        return stations;

    }

    public static Object[][] getExcelData(
            String filePath,
            String sheetName) throws IOException {


        FileInputStream inputStream =
                new FileInputStream(filePath);


        Workbook workbook =
                new XSSFWorkbook(inputStream);


        Sheet sheet =
                workbook.getSheet(sheetName);



        int rows =
                sheet.getPhysicalNumberOfRows();


        int columns =
                sheet.getRow(0)
                     .getLastCellNum();



        Object[][] data =
                new Object[rows - 1][columns];



        for(int i = 1; i < rows; i++) {


            for(int j = 0; j < columns; j++) {


                data[i-1][j] =
                    sheet.getRow(i)
                         .getCell(j)
                         .toString();
            }

        }

        workbook.close();

        inputStream.close();

        return data;

    }


}
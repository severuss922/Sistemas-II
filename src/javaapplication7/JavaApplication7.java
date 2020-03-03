/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication7;

import java.io.FileNotFoundException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mmarq
 */
public class JavaApplication7 {

    private static String[] columns = {"Name", "Email", "Date Of Birth", "Salary"};
    private static List<Empleado> employees = new ArrayList<>();

    static {
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(1992, 7, 21);
        employees.add(new Empleado("Joaquin Sabaté", "rajeev@example.com",
                dateOfBirth.getTime(), 1200000.0));

        dateOfBirth.set(1965, 10, 15);
        employees.add(new Empleado("Thomas cook", "thomas@example.com",
                dateOfBirth.getTime(), 1500000.0));

        dateOfBirth.set(1987, 4, 18);
        employees.add(new Empleado("Steve Maiden", "steve@example.com",
                dateOfBirth.getTime(), 1800000.0));
    }

    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();

        Sheet sheet = workbook.createSheet("Employee");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for (Empleado employee : employees) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(employee.getName());

            row.createCell(1)
                    .setCellValue(employee.getEmail());

            Cell dateOfBirthCell = row.createCell(2);
            dateOfBirthCell.setCellValue(employee.getDateOfBirth());
            dateOfBirthCell.setCellStyle(dateCellStyle);

            row.createCell(3)
                    .setCellValue(employee.getSalary());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = null;
        
        try {
            fileOut = new FileOutputStream("poi-generated-file.xlsx");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
        }
        workbook.write(fileOut);
        
        fileOut.close();

        workbook.close();
    }
}

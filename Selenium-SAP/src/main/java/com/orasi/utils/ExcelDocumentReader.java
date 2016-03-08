package com.orasi.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDocumentReader {
    private XSSFSheet excelWSheet;
    private XSSFWorkbook excelWBook;
    private XSSFCell cell;
    private String filepath;
    
    public ExcelDocumentReader(String filepath){
	this.filepath = filepath;
    }
    
    public ExcelDocumentReader(){}

    /**
     * This gets the test data from excel workbook by the sheet specified.  It returns all the data 
     * as a 2d array
     * 
     * @param	sheetName	the excel sheet
     * @version	10/16/2014
     * @author 	Justin Phlegar
     * @return 	2d array of test data
     */
    

    public Object[][] readData(String sheetName) {
    	return (readData(sheetName, -1));
    }
    
    public Object[][] readData(String filepath, String sheetName) {
	return (readData(filepath, sheetName, -1));
    }

    public Object[][] readData(String sheetName, int rowToRead) {
	return readData(filepath, sheetName, rowToRead);
    }

    public Object[][] readData(String filepath, String sheetName, int rowToRead) {

	String[][] tabArray = null;
	int totalRows =  1;

	try {

		FileInputStream excelFile = new FileInputStream(filepath);

		// Access the required test data sheet

		excelWBook = new XSSFWorkbook(excelFile);

		excelWSheet = excelWBook.getSheet(sheetName);

		int startRow = 1;

		int startCol = 0;

		int ci, cj;

		
		if (rowToRead == -1) totalRows = excelWSheet.getLastRowNum();

		// you can write a function as well to get Column count

		int totalCols = excelWSheet.getRow(startRow).getLastCellNum();

		tabArray = new String[totalRows][totalCols];

		ci = 0;

		
		cj = 0;
		
		for (int i = startRow; i <= totalRows; i++, ci++) {
			cj = 0;
			for (int j = startCol; j < totalCols; j++, cj++) {					
				tabArray[ci][cj] = getCellData(i, j);
			}
		}
		
	}catch (FileNotFoundException e) {
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}catch (IOException e) {
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}
	return (tabArray);
    }
    
	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	private String getCellData(int rowNum, int colNum) {
		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}
}

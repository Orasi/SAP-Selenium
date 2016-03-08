package com.orasi.utils.dataProviders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataProvider {
	
	//Constructor
	public CSVDataProvider(){
		
	}

	/**
	 * This gets the test data from a csv file.  It returns all the data 
	 * as a 2d array
	 * 
	 * @param	filePath		the file path of the CSV file
	 * @version	12/18/2014
	 * @author 	Jessica Marshall
	 * @return 	2d array of test data
	 */
	public static Object[][] getTestScenarioData(String filePath){
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		String[][] dataArray = null;
		List<String>csvRowList = new ArrayList<String>();
		String[] rowSplit;
		int columnCount = 0;
		int rowCount = 0;
		
		// Get the file location from the project main/resources folder
		filePath =  CSVDataProvider.class.getResource(filePath).getPath();

		// in case file path has a %20 for a whitespace, replace with actual
		// whitespace
		filePath = filePath.replace("%20", " ");
		
		
		//open the CSV and add each line into a list		
		try {
			br = new BufferedReader(new FileReader(filePath));
			while((line = br.readLine()) !=null) {
				csvRowList.add(line);
			}
			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//column count, split the first row and get the length 
		columnCount = csvRowList.get(0).split(csvSplitBy).length;
		
		//row count - use size of list -1, since the first row is the header
		rowCount = csvRowList.size()-1;
		
		//create size of array, 
		dataArray = new String[rowCount][columnCount];
		
		
		
		//transform the list into 2d array
		//start at row 1 since, first row 0 is column headings
		for (int rowNum = 1; rowNum <= rowCount; rowNum++) {
			
			//take the row which is a string, and split it
			rowSplit = csvRowList.get(rowNum).split(csvSplitBy);
			
			for (int colNum = 0; colNum < columnCount; colNum++ ) {
				dataArray[rowNum-1][colNum] = rowSplit[colNum];
				System.out.println(dataArray[rowNum-1][colNum]);
			}
			System.out.println("");
		}
		
		return dataArray;
	}
	
}

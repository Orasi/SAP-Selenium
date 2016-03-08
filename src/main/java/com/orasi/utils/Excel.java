package com.orasi.utils;



import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
//import org.apache.xpath.operations.String;

import java.io.File;
import java.io.IOException;


/**
 * Created by Adam on 12/22/2015.
 */
public class Excel {

    //Variables
    private static String filePath;
    private static String sheetName;
    private static Workbook wb;
    private static Sheet sh;


    public Excel(String filePath){
        this.filePath = filePath;

        try {
            wb = WorkbookFactory.create(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        //setting default sheet.
        sh = wb.getSheetAt(0);
    }

    public String GetCellString (int cellrow, int cellcol){
        //Sheet mySheet = wb.getSheet("Sheet1");
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
        CellReference cellReference = new CellReference(cellrow,cellcol);
        Row row = sh.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());
        return cell.getStringCellValue();

    }

    public Excel(String filePath, String sheetName){
        this.filePath = filePath;
        this.sheetName = sheetName;


    }
}

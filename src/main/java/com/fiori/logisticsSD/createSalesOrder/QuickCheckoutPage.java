package com.fiori.logisticsSD.createSalesOrder;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.fiori.BasePage;
import com.fiori.logisticsSD.SelectCustomerPage;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Checkbox;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.Webtable;
import com.orasi.core.interfaces.impl.ButtonImpl;
import com.orasi.core.interfaces.impl.ElementImpl;
import com.orasi.core.interfaces.impl.TextboxImpl;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.utils.WindowHandler;
import com.orasi.utils.date.DateTimeConversion;

public class QuickCheckoutPage extends BasePage{
    private OrasiDriver driver = null;
    private HashMap<String, Object> dataMap = new HashMap<String, Object>();
    private int colItemName = 2;
    private int colItemQuantity = 3;
    private int colItemDelivery = 4;
    private int colItemAvailableQuantity = 5;
    private int colItemEstimatedDelivery = 6;
    private int colItemPrice = 7;
    private int colItemTotal = 8;
    private int colItemRemove = 9;
    
    @FindBy(xpath="//input[contains(@id,'SOC_PAC_SINGLE_RDD')]") private Textbox txtRequestDeliveryDate;
    @FindBy(xpath="//input[contains(@id,'SOC_SINGLE_SHIPMENT_CHECKBOX-CB')]") private Checkbox chkSingleShipment;
    @FindBy(xpath="//div[contains(@id,'items-header') and contains(text(),'Items ')]") private Label lblItemsInTable;    
    @FindBy(xpath="//table[contains(@id,'items-listUl')]") private Webtable tblItems;
    @FindBy(xpath="//button/div[contains(@class,'sapMBtnDefault')]/span[text()='Checkout']/../..") private Button btnCheckout;
    

    // *********************
    // ** Build page area **
    // *********************
    public QuickCheckoutPage(OrasiDriver driver){
		this.driver = driver;		
		ElementFactory.initElements(driver, this);
    }
	
    public boolean pageLoaded(){
    	driver.page().isDomComplete();
    	waitUntilLoadingComplete(driver);
    	return txtRequestDeliveryDate.syncEnabled();
    }
    
    public void initialize(){
    	ElementFactory.initElements(driver, this);	
    }
	
    // *****************************************
    // ***Page Interactions ***
    // *****************************************

    public void setDeliveryDate(String daysOut){
    	txtRequestDeliveryDate.set(DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    }
    
    public boolean validateMainDeliveryDate(String daysOut){
    	return txtRequestDeliveryDate.getText().equals(DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    }
    
    public boolean validateNumberOfItems(){
    	String expectedNumberOfItems = driver.data().get("Product Details: Items in Shopping Cart").toString();
    	String printedNumberOfItems = lblItemsInTable.getText().replace("Items (", "").replace(")", "");
    	return  expectedNumberOfItems.equals(printedNumberOfItems);
    }
   
    public HashMap<String, Object> storeInfo(){
       	return dataMap;
    }
    
    public String getItemQuantity(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemQuantity).findElement(By.tagName("input")).getText();
    }
    
    public void updateItemQuantity(String itemName, String quantity){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new TextboxImpl(tblItems.getCell(itemRow, colItemQuantity).findElement(By.tagName("input"))).set(quantity);
    	dataMap.put("Product [" + itemName + "] Details: Number of Units" , quantity);
    }
    
    public String getItemDelivery(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemDelivery).findElement(By.tagName("input")).getText();
    }
    
    public void updateItemDelivery(String itemName, String daysOut){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new TextboxImpl(tblItems.getCell(itemRow, colItemDelivery).findElement(By.tagName("input"))).set(DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    }
    
    public String getItemAvailableQuantity(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemAvailableQuantity).findElement(By.tagName("input")).getText();
    }
    
    public String getItemEstimatedDelivery(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemEstimatedDelivery).findElement(By.tagName("input")).getText();
    }
    
    public String getItemPrice(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemPrice).findElement(By.tagName("span")).getText();
    }

    public String getItemTotal(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	return tblItems.getCell(itemRow, colItemTotal).findElement(By.tagName("span")).getText();
    }
    
    public String getGrandTotal(){
    	return tblItems.findElement(By.xpath("./tfoot/.//span[@class='sapMObjectNumberText']")).getText();
    }
    
    public void removeItem(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new ButtonImpl(tblItems.getCell(itemRow, colItemRemove).findElement(By.tagName("button"))).click();
    }
    
    public void validateDataInTable(){
    	String dataName = "";
    	for(String item : driver.data().get("Products").toString().split(";")){
    		dataName = "Product [" + item + "] Details: ";
    		int itemRow = tblItems.getRowWithCellText(item, colItemName, 1, false);
    		TestReporter.assertTrue(validateData(itemRow, colItemName, dataName + "Number", false), "Verify [" + item + "] Item Number");
    		TestReporter.assertTrue(validateData(itemRow, colItemQuantity, dataName + "Number of Units", true), "Verify [" + item + "] Requested Quantity");
    		TestReporter.assertTrue(validateData(itemRow, colItemDelivery, dataName + "Requested Delivery Date",true), "Verify [" + item + "] Requested Delivery");
    		TestReporter.assertTrue(validateData(itemRow, colItemPrice, dataName + "Price Per Unit",false), "Verify [" + item + "] Price per Unit");
    		
    	}
    }
    
    private boolean validateData(int row, int column, String key, boolean isTextbox){
    	if(isTextbox)return tblItems.getCell(row, column).findElement(By.tagName("input")).getAttribute("value").contains(driver.data().get(key).toString());
    	else return tblItems.getCell(row, column).getText().contains(driver.data().get(key).toString());
    }
}

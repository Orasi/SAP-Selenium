package com.fiori.logisticsSD.createSalesOrder;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.fiori.BasePage;
import com.fiori.logisticsSD.SelectCustomerPage;
import com.orasi.core.interfaces.Button;
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

public class SOShoppingCartPage extends BasePage{
    private OrasiDriver driver = null;
    private HashMap<String, Object> dataMap = new HashMap<String, Object>();
    private int numberOfProductsAdded = 0;
    private int colItemName = 2;
    private int colItemProductNumber = 3;
    private int colItemQuantity = 4;
    private int colItemDelivery = 5;
    private int colItemRemove = 6;
    
    @FindBy(xpath="//input[contains(@id,'SOC_CART_SINGLE_RDD')]") private Textbox txtRequestDeliveryDate;
    @FindBy(xpath="//div[contains(@id,'SOC_CART_LIST-header') and contains(text(),'Items ')]") private Label lblItemsInTable;    
    @FindBy(xpath="//table[contains(@id,'SOC_CART_LIST-listUl')]") private Webtable tblItems;
    @FindBy(xpath="//button/div[contains(@class,'sapMBtnDefault')]/span[text()='Checkout']/../..") private Button btnCheckout;
    

    // *********************
    // ** Build page area **
    // *********************
    public SOShoppingCartPage(OrasiDriver driver){
		this.driver = driver;		
		ElementFactory.initElements(driver, this);
    }
	
    public boolean pageLoaded(){
    	driver.page().isDomComplete();
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
    	txtRequestDeliveryDate.onBlur();
    	for(String item : driver.data().get("Products").toString().split(";")){
    		dataMap.put("Product [" + item + "] Details: Requested Delivery Date", DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    	}
    }
    
    public boolean validateNumberOfItems(){
    	String expectedNumberOfItems = driver.data().get("Product Details: Items in Shopping Cart").toString();
    	String printedNumberOfItems = lblItemsInTable.getText().replace("Items (", "").replace(")", "");
    	return  expectedNumberOfItems.equals(printedNumberOfItems);
    }
   
    public HashMap<String, Object> storeInfo(){
       	return dataMap;
    }
    
    public void updateItemQuantity(String itemName, String quantity){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new TextboxImpl(tblItems.getCell(itemRow, colItemQuantity).findElement(By.tagName("input"))).set(quantity);
    	dataMap.put("Product [" + itemName + "] Details: Number of Units" , quantity);
    }
    
    public void updateItemDelivery(String itemName, String daysOut){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new TextboxImpl(tblItems.getCell(itemRow, colItemDelivery).findElement(By.tagName("input"))).set(DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    	dataMap.put("Product [" + itemName + "] Details: Requested Delivery Date", DateTimeConversion.getDaysOut(daysOut, "MMM dd, yyyy"));
    }

    public void removeItem(String itemName){
    	int itemRow = tblItems.getRowWithCellText(itemName, colItemName);
    	new ButtonImpl(tblItems.getCell(itemRow, colItemRemove).findElement(By.tagName("button"))).click();
    }
    
    public void checkout(){
    	btnCheckout.click();
    	WindowHandler.waitUntilURLContains(driver, "quickCheckout", 10);
    }
}

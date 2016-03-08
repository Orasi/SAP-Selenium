package com.fiori.logisticsSD.createSalesOrder;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.fiori.BasePage;
import com.fiori.logisticsSD.SelectCustomerPage;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Label;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.Webtable;
import com.orasi.core.interfaces.impl.ElementImpl;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;

public class SalesOrdersPage extends BasePage{
    private OrasiDriver driver = null;

    private final String xpathSidePanel = "//div[contains(@class,'sapMSplitContainerMaster')]/.";
    	@FindBy(xpath=xpathSidePanel + "//label[contains(@id,'MasterListHeaderTitle')]") private Label lblCustomerName;
    	@FindBy(xpath=xpathSidePanel + "//button[contains(@id,'changeCustomerButton')]") private Button btnChangeCustomer;
    	@FindBy(xpath=xpathSidePanel + "//input[@type='search']") private Textbox txtSidePanelSearch ;
    	@FindBy(xpath=xpathSidePanel + "//li[contains(@id,'ORDERS_ID')]") private Button btnShowOrders;
    	@FindBy(xpath=xpathSidePanel + "//li[contains(@id,'PRODUCTS')]") private Button btnShowProducts;  
    	

    private final String xpathMainPanel = "//div[contains(@class,'sapMSplitContainerDetail')]/.";
    	@FindBy(xpath=xpathMainPanel + "//section[contains(@id,'ProductDetail-cont')]/.//div[contains(@id,'header-titleText-inner')]") private Element eleItemName;
    	@FindBy(xpath=xpathMainPanel + "//table[contains(@id,'InformationListLeft-listUl')]") private Webtable tblItemInformation;

    // *********************
    // ** Build page area **
    // *********************
    public SalesOrdersPage(OrasiDriver driver){
	this.driver = driver;		
	ElementFactory.initElements(driver, this);
    }
	
    public boolean pageLoaded(){
	return lblCustomerName.syncVisible();
    }
    
    public void initialize(){
	ElementFactory.initElements(driver, this);	
    }
	
    // *****************************************
    // ***Page Interactions ***
    // *****************************************
    public boolean customerLoaded(String customerFullName){
	return lblCustomerName.syncTextInElement(customerFullName);
    }
    
    public SelectCustomerPage changeCustomer(){
	btnChangeCustomer.click();
	return new SelectCustomerPage(driver);
    }
    
    public void search(String orderOrProduct){
	txtSidePanelSearch.set(orderOrProduct);
    }
    
    public void selectItemByName(String name){
	driver.findLabel(By.xpath(xpathSidePanel + "//ul/li[contains(@class,'sapMObjLItem')]/.//div[@class='sapMTextMaxLine' and text()='" + name +"']")).click();
	eleItemName.syncTextInElement(name);
    }
    
    public void showOrders(){
	btnShowOrders.click();
	driver.page().isDomComplete();
	lblCustomerName.syncTextInElement("Sales Orders");
    }
    
    public void showProducts(){
	btnShowProducts.click();
	driver.page().isDomComplete();
	lblCustomerName.syncTextInElement("Products");
    }
    
    public boolean getInformationDetails(String columnName, String textInColumn){
	int rowPosition = tblItemInformation.getRowWithCellText(columnName);	
	if (rowPosition == 0) throw new RuntimeException("Row with information column [" +columnName + "] not found");
	return tblItemInformation.validateCellDataMatches(rowPosition, 2, textInColumn, 1);
    }
}

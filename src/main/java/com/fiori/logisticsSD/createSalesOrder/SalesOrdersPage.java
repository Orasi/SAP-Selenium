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
import com.orasi.core.interfaces.impl.ElementImpl;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.utils.WindowHandler;

public class SalesOrdersPage extends BasePage{
    private OrasiDriver driver = null;
    private HashMap<String, Object> dataMap = new HashMap<String, Object>();
    
    @FindBy(xpath="//button[contains(@id,'add')]/div[contains(@class,'sapMBtnDefault')]/span[text()='Add To Cart']/../..") private Button btnAddToCart;
    @FindBy(xpath="//button[contains(@id,'CART_BUTTON')]") private Button btnCart;
    @FindBy(xpath="//span[@class='sapMObjectAttributeText' and contains(text(),'Product No')]") private Label lblProductNumber;
    @FindBy(xpath="//div[contains(@class,'sapMMessageToast')]") private Label eleAddedItemsMessage;
    
    private final String xpathSidePanel = "//div[contains(@class,'sapMSplitContainerMaster')]/.";
    	@FindBy(xpath=xpathSidePanel + "//label[contains(@id,'MasterListHeaderTitle')]") private Label lblCustomerName;
    	@FindBy(xpath=xpathSidePanel + "//button[contains(@id,'changeCustomerButton')]") private Button btnChangeCustomer;
    	@FindBy(xpath=xpathSidePanel + "//input[@type='search']") private Textbox txtSidePanelSearch ;
    	@FindBy(xpath=xpathSidePanel + "//li[contains(@id,'ORDERS_ID')]") private Button btnShowOrders;
    	@FindBy(xpath=xpathSidePanel + "//li[contains(@id,'PRODUCTS')]") private Button btnShowProducts;  
    	

    private final String xpathMainPanel = "//div[contains(@class,'sapMSplitContainerDetail')]/.";
    	@FindBy(xpath=xpathMainPanel + "//section[contains(@id,'ProductDetail-cont')]/.//div[contains(@id,'header-titleText-inner')]") private Element eleItemName;
    	@FindBy(xpath=xpathMainPanel + "//section[contains(@id,'ProductDetail-cont')]/.//div[contains(@id,'header-number')]/span[@class='sapMObjectNumberText']") private Element eleItemPricePerUnit;
    	@FindBy(xpath=xpathMainPanel + "//section[contains(@id,'ProductDetail-cont')]/.//div[contains(@id,'header-number')]/span[@class='sapMObjectNumberUnit']") private Element eleItemPriceUnitDescription;
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
//		String productId = driver.findLabel(By.xpath(xpathSidePanel + "//ul/li[contains(@class,'sapMObjLItem')]/.//div[@class='sapMTextMaxLine' and text()='" + name +"']/../../../.././/div[@class='sapMObjectAttributeDiv']/span")).getText();
//		WindowHandler.waitUntilURLContains(driver, productId, 5);
//		initialize();
		Sleeper.sleep(2000);
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
    
    public void addProductToCart(){
    	int itemsInCartPrior = Integer.valueOf(btnCart.getText());
    	btnAddToCart.click();
    	eleAddedItemsMessage.syncTextInElement("Your products are saved to the cart", 3, false);
    	int itemsInCartAfter = Integer.valueOf(btnCart.getText());
    	TestReporter.assertEquals(itemsInCartPrior + 1, itemsInCartAfter, "Number of items in cart updated");
    	
    	String itemName = eleItemName.getText();
    	dataMap.put("Product [" + itemName + "] Details: Number", lblProductNumber.getText().replace("Product No.: ", ""));
    	dataMap.put("Product [" + itemName + "] Details: Price Per Unit" , eleItemPricePerUnit.getText());
    	dataMap.put("Product [" + itemName + "] Details: Unit Description" , eleItemPriceUnitDescription.getText());
    }
    
    public void goToShoppingCart(){
    	btnCart.click();
    	WindowHandler.waitUntilURLContains(driver, "soCreateCart", 10);
    }
    
    public HashMap<String, Object> storeInfo(){
    	dataMap.put("Product Details: Items in Shopping Cart" , btnCart.getText());
       	return dataMap;
    };
}

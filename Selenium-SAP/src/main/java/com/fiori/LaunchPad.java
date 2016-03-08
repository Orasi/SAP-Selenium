package com.fiori;

import org.openqa.selenium.support.FindBy;

import com.fiori.logisticsSD.SelectCustomerPage;
import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;

public class LaunchPad extends BasePage{
    private OrasiDriver driver = null;
    
    @FindBy(className="eula") private Element eleEULA;
    @FindBy(xpath="//div[contains(@class,'eula')]/footer/button/div/span[text()='I agree']/../..") private Button btnEulaIAgree;
    	
    //Cross elements
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Inbox')]") private Button btnMyInbox;
    
    //Human Capital Management Elements
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Time Events')]") private Button btnMyTimeEvent;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Paystubs')]") private Button btnMyPaystub;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Benefits')]") private Button btnMyBenefits;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Leave Requests')]") private Button btnMyLeaveRequests;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'Approve Leave Requests')]") private Button btnApproveLeaveRequests;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Timesheet')]") private Button btnMyTimesheet;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'Approve Timesheet')]") private Button btnApproveTimesheet;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'Employee Lookup')]") private Button btnEmployeeLookup;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Team Calendar')]") private Button btnTeamCalendar;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'People Profile')]") private Button btnPeopleProfile;
    
    //CRM elements
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Tasks')]") private Button btnMyTasks;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Appointments')]") private Button btnMyApointments;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Contacts')]") private Button btnMyContacts;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'Simulate Sales Pipeline')]") private Button btnSimulateSalesPipeline;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Leads')]") private Button btnMyLeads;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Accounts')]") private Button btnMyAccounts;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'My Opportunties')]") private Button btnMyOpportunities;
    @FindBy(xpath = "//div[@class='sapUshellTileBase' and contains(@title,'Analyze Sentiments')]") private Button btnAnalyzeSentiments;
    
    //Retail elements 
    @FindBy(xpath = "//div[contains(@title,'Look Up Retail Products')]") private Button btnLookUpRetailProducts;
    @FindBy(xpath = "//div[contains(@title,'Transfer Stock')]") private Button btnTransferStock;
    @FindBy(xpath = "//div[contains(@title,'Order Products')]") private Button btnOrderProducts;
    @FindBy(xpath = "//div[contains(@title,'Adjust Stock')]") private Button btnAdjustStock;
    
    //Travel and Expense elements
    @FindBy(xpath = "//div[contains(@title,'My Travel and Expense')]") private Button btnMyTravelAndExpese;
    @FindBy(xpath = "//div[contains(@title,'Approve Travel Expenses')]") private Button btnApproveTravelExpenses;
    @FindBy(xpath = "//div[contains(@title,'My Travel Request')]") private Button btnMyTravelRequest;
    @FindBy(xpath = "//div[contains(@title,'Approve Travel Requests')]") private Button btnApproveTravelRequests;
    
    //Logistics (SD) elements
    @FindBy(xpath = "//div[contains(@title,'Create Sales Order')]") private Button btnCreateSalesOrder;
    @FindBy(xpath = "//div[contains(@title,'Check Price and Availability')]") private Button btnCheckPriceAndAvail;
    @FindBy(xpath = "//div[contains(@title,'Track Sales Orders')]") private Button btnTrackSalesOrders;
    @FindBy(xpath = "//div[contains(@title,'Change Shipping Address')]") private Button btnChangeShippingAddress;
    @FindBy(xpath = "//div[contains(@title,'Customer Invoices')]") private Button btnCustomerInvoices;
    
    //Logistics (MM) elements
    @FindBy(xpath = "//div[contains(@title,'Approve Purchase Contracts')]") private Button btnApprovePurchaseContracts;
    @FindBy(xpath = "//div[contains(@title,'Approve Purchase Orders')]") private Button btnApprovePurchaseOrders;
    @FindBy(xpath = "//div[contains(@title,'Order from Requisitions')]") private Button btnOrderFromRequisitions;
    @FindBy(xpath = "//div[contains(@title,'Approve Requisitions')]") private Button btnApproveRequisitions;
    
    //Finance elements
    @FindBy(xpath = "//div[contains(@title,'My Spend')]") private Button btnMySpend;
    @FindBy(xpath = "//div[contains(@title,'Cash Position - Today')]") private Button btnCashPosition;
    @FindBy(xpath = "//div[contains(@title,'Liquidity Forecast')]") private Button btnLiquidityForecast;
    @FindBy(xpath = "//div[contains(@title,'DSO')]") private Button btn;
    
    // *********************
    // ** Build page area **
    // *********************
    public LaunchPad(OrasiDriver driver){
	this.driver = driver;		
	ElementFactory.initElements(driver, this);
    }
	
    public boolean pageLoaded(){
	return driver.page().isDomComplete();
    }
	
    // *****************************************
    // ***Page Interactions ***
    // *****************************************

    public void handleEula(){	
	do {
	    btnEulaIAgree.focus();
	    btnEulaIAgree.click();
	} while (!btnEulaIAgree.syncHidden(1, false));
    }
    
    public void logisticsSD_CreateSalesOrder(){
	navigateTo(btnCreateSalesOrder);
    }
    
    private void navigateTo(Button buttonOption){
	buttonOption.syncPresent();
	buttonOption.scrollIntoView();
	buttonOption.click();
	waitUntilLoadingComplete(driver);
    }
    
}

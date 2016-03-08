package com.fiori.logisticsSD;

import org.openqa.selenium.support.FindBy;

import com.fiori.BasePage;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;

public class SelectCustomerPage extends BasePage{
    private OrasiDriver driver = null;
  
    @FindBy(xpath="//div[@role='dialog']/header") private Element eleDialogHeader;
    
    @FindBy(xpath="//input[@type='search']") private Textbox txtSearch;
    @FindBy(xpath="//div[@role='dialog']/section//div[@role='presentation']/ul/li") private Element eleFirstCustomer;
    
    @FindBy(xpath="//div[@role='dialog']/footer/.//button/div/span[contains(text(),'OK')]") private Element eleFooterOK;
    
    
  
    
    // *********************
    // ** Build page area **
    // *********************
    public SelectCustomerPage(OrasiDriver driver){
	this.driver = driver;		
	ElementFactory.initElements(driver, this);
    }
	
    public boolean pageLoaded(){
	return eleDialogHeader.syncTextInElement("Select Customer");
    }
    
    // *****************************************
    // ***Page Interactions ***
    // *****************************************

   public void searchForCustomer(String customer){
       txtSearch.set(customer);
   }
   
   public void selectCustomer(){
       eleFirstCustomer.click();
       eleFooterOK.click();
   }
}


package com.fiori;

import org.openqa.selenium.support.FindBy;

import com.orasi.core.interfaces.Button;
import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.Textbox;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.utils.OrasiDriver;

public class TopNavigationBar extends BasePage{
    private OrasiDriver driver = null;
	//all the page elements
	@FindBy(id = "Options") private Element eleOptionMenu;	
	@FindBy(id = "Search") private Element eleSearch;	
	@FindBy(id = "configBtn") private Button btnConfiguration;	
	@FindBy(className = "alert-danger") private Element eleAlert;	
	
	// *********************
	// ** Build page area **
	// *********************
	public TopNavigationBar(OrasiDriver driver){
		this.driver = driver;		
		ElementFactory.initElements(driver, this);
	}
	
	public boolean pageLoaded(){
	    return driver.page().pageLoaded(this.getClass(), eleOptionMenu);
	}
	
	// *****************************************
	// ***Page Interactions ***
	// *****************************************
}

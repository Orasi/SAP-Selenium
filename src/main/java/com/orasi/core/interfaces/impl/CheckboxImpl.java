package com.orasi.core.interfaces.impl;

import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.Checkbox;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;

/**
 * Wrapper class like Select that wraps basic checkbox functionality.
 */ 
public class CheckboxImpl extends ElementImpl implements Checkbox {
	//private java.util.Date dateAfter= new java.util.Date();
    /**
     * Wraps a WebElement with checkbox functionality.
     *
     * @param element to wrap up
     */
    public CheckboxImpl(WebElement element) {
        super(element);
    }

    
    @Override
    public void toggle() {
        getWrappedElement().click();
    }

    @Override
    public void jsToggle() {
    	getWrappedDriver().executeJavaScript("arguments[0].click();", element);
    }

    @Override
    public void check() {
        if (!isChecked()) {
            try{
        	toggle();
            }catch(RuntimeException rte){
        	TestReporter.interfaceLog(" Checking the Checkbox [ <b>@FindBy: " + getElementLocatorInfo()  + " </b>]", true);
         	throw rte;
            }
            	TestReporter.interfaceLog(" Checking the Checkbox [ <b>@FindBy: " + getElementLocatorInfo()  + " </b>]");
        }
    }

    @Override
    public void uncheck() {
        if (isChecked()) {
            try{
        	toggle();
            }catch(RuntimeException rte){
                TestReporter.interfaceLog(" Unchecking the Checkbox [ <b>@FindBy: " + getElementLocatorInfo()  + " </b>]", true);
                throw rte;
            }
      	
            TestReporter.interfaceLog(" Unchecking the Checkbox [ <b>@FindBy: " + getElementLocatorInfo()  + " </b>]");
          
        }
    }

    @Override
    public boolean isChecked() {
        return getWrappedElement().isSelected();
    }
}
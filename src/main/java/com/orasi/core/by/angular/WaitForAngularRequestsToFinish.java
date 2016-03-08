package com.orasi.core.by.angular;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestEnvironment;

/*
 * Original Code from https://github.com/paul-hammant/ngWebDriver
 */

public class WaitForAngularRequestsToFinish extends TestEnvironment {

    public WaitForAngularRequestsToFinish(TestEnvironment te) {
		super(te);
		// TODO Auto-generated constructor stub
	}

	public static void waitForAngularRequestsToFinish(WebDriver driver) {
		if(driver instanceof OrasiDriver) {
			driver = ((OrasiDriver)driver).getWebDriver();
		} 		    	
    	
		((JavascriptExecutor) driver).executeAsyncScript("var callback = arguments[arguments.length - 1];" +
    				"angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
   
    }
}
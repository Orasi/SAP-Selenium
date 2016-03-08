package com.orasi.utils;

import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class AlertHandler {
	
	private static void alertHandler(WebDriver driver){
		try {
			Reporter.log(new Timestamp( new java.util.Date().getTime()) + " :: Closing alert popup with text [ <i>" + driver.switchTo().alert().getText() +" </i> ]<br />");
			driver.switchTo().alert().accept();
            	        driver.switchTo().defaultContent();
            	        
            	    } catch (Exception e) {
            	        //exception handling
            	    }
	}
	
	public static boolean isAlertPresent(WebDriver driver, int timeout){
            try{
            	WebDriverWait wait = new WebDriverWait(driver, timeout);
    	        wait.until(ExpectedConditions.alertIsPresent());
    	        return true;
            }
            catch(Exception e){
                return false;
            }
        }
	
	public static void handleAllAlerts(WebDriver driver, int timeout){
            while (isAlertPresent(driver, timeout)){
        	alertHandler(driver);
            }
	}
	
	public static void handleAlert(WebDriver driver, int timeout){
            if(isAlertPresent(driver, timeout)) alertHandler(driver);
	}
}

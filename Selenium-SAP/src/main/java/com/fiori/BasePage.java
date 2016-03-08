package com.fiori;

import org.openqa.selenium.By;

import com.orasi.utils.OrasiDriver;

public class BasePage {
	
    public static void waitUntilLoadingComplete(OrasiDriver driver){
	driver.findElement(By.id("loadingDialog")).syncCssPropertyContainsValue("display", "block", 3, false);
	driver.findElement(By.id("loadingDialog")).syncCssPropertyContainsValue("display", "none");
    }
    	public static boolean isAlertVisible(OrasiDriver driver){	    
      	    return  !driver.findElement(By.xpath("//*[@id='alertdialog']")).syncCssPropertyContainsValue("display", "none", 3, false);
      	}
      	
      	public static String getAlertType(OrasiDriver driver){
      	    return driver.findElement(By.xpath("//*[@id='alertdialog']/*//header/div/div/h1[contains(@class,'sapMTitle')]")).getText();
      	}
      	
    
      	public static String getAlertText(OrasiDriver driver){
      	    return driver.findElement(By.xpath("//*[@id='alertdialog']/*//section/div/div/span")).getText();
      	}
      	
      	public static void closeAlert(OrasiDriver driver){
      	  driver.findElement(By.xpath("//*[@id='alertdialog']/*//footer/button[text()='OK']")).click();
      	}
}

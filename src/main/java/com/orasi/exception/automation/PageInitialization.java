package com.orasi.exception.automation;

import org.openqa.selenium.WebDriver;

import com.orasi.exception.AutomationException;

public class PageInitialization extends AutomationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3407361723082329697L;


	public PageInitialization(String message, WebDriver driver){
		super( message, driver);
	}
	
}

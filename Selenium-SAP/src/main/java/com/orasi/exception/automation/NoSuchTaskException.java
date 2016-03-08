package com.orasi.exception.automation;

import org.openqa.selenium.WebDriver;

import com.orasi.exception.AutomationException;

public class NoSuchTaskException extends AutomationException {
	/**
     * 
     */
    private static final long serialVersionUID = -1985117339792858897L;

	public NoSuchTaskException(){
		super();		
	}
	
	public NoSuchTaskException(String message){
		super(message);
	}
	
	public NoSuchTaskException(String message, WebDriver driver){
		super(message, driver);
	}
	
	public NoSuchTaskException(String message, Throwable cause){
		super(message, cause);
	}
}

package com.orasi.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.orasi.utils.date.SimpleDate;

public class TestReporter {
    private static boolean printToConsole = true;
    private static boolean printClassPath= true;
    
    private static String getTimestamp() {
	String date = SimpleDate.getTimestamp().toString().substring(11);
	return  date + " :: ";
    }
    
    private static String trimHtml(String log){
	return log.replaceAll("<[^>]*>", "");
    }
	
    private static String getClassPath(){
	String path = " > ";
	if(getPrintFullClassPath()){
        	StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        	int x = 0;
        	String filename = "";
        	for(StackTraceElement element : elements){
        		filename = element.getClassName().toString();
        		if(x == 0 || x == 1 || x == 2) {
        			x++;
        			continue;
        		}else if(!filename.contains("sun.reflect")  &&
        			!filename.contains("com.orasi.utils.TestReporter") &&
        			!filename.contains("com.orasi.utils.PageLoaded") &&
        			!filename.contains("java.lang.reflect") &&
        			!filename.contains("java.lang.Thread") &&
        			!filename.contains("com.orasi.core.interfaces") &&
        			!filename.contains("com.sun.proxy") && //
        			!filename.contains("org.testng.internal") &&
        			!filename.contains("java.util.concurrent.ThreadPoolExecutor") &&
        			!filename.contains("com.orasi.utils.debugging")&&
        			!filename.contains("java.util.HashMap")&&
        			!element.getMethodName().contains("lambda"))
        		{
        			path = element.getClassName()+"#"+element.getMethodName();
        			break;
        		}
        		
        	}
	}
	return path + " > ";
    }
    
    public static void setPrintToConsole(boolean printToConsole){
	TestReporter.printToConsole = printToConsole;
    }
    
    public static boolean getPrintToConsole(){
	return printToConsole;
    }
    
    
    public static void setPrintFullClassPath(boolean printClassPath){
	TestReporter.printClassPath = printClassPath;
    }
    
    public static boolean getPrintFullClassPath(){
	return printClassPath;
    }
    
    public static void logStep(String step) {
	Reporter.log("<br/><b><font size = 4>Step: " + step
		+ "</font></b><br/>");
	if(getPrintToConsole()) System.out.println(step);
    }

    /*
     * public static void logScenario(){
     * Reporter.log("<br/><b><font size = 4>Data Scenario: " +
     * Datatable.getCurrentScenario()+ "</font></b><br/>"); }
     */

    public static void logScenario(String scenario) {
	Reporter.log("<br/><b><font size = 4>Data Scenario: " + scenario
		+ "</font></b><br/>");
	if(getPrintToConsole()) System.out.println(getTimestamp() + trimHtml(scenario));
    }

    public static void interfaceLog(String message) {
	Reporter.log(getTimestamp() + getClassPath() + message + "<br />");
	if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + trimHtml(message.trim()));
    }
    
    public static void interfaceLog(String message, boolean failed) {
	Reporter.log(getTimestamp() + "<font size = 2 color=\"red\">" + getClassPath() + message + "</font><br />");
	if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + trimHtml(message.trim()));
    }

    public static void log(String message) {
	Reporter.log(getTimestamp() + " <i><b>" + getClassPath() + message + "</b></i><br />");
	if(getPrintToConsole()) System.out.println(getTimestamp() + getClassPath() + trimHtml(message));
    }
    
    public static void logFailure(String message){
	Reporter.log(getTimestamp() + " <font size = 2 color=\"red\"><b><u> FAILURE: " +getClassPath() +  message + "</font></u></b><br />");
	if(getPrintToConsole()) System.out.println(getTimestamp() + trimHtml( "FAILURE: " + getClassPath() + message ));
    }

    public static void assertTrue(boolean condition, String description) {	
	try{
	    Assert.assertTrue(condition, description);
	}catch (AssertionError failure){
	    logFailure("Assert True - " + description );
	    if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert True - " + trimHtml(description));
	    Assert.fail(description);
	}
	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert True - " + description + "</font></u></b><br />");
	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert True - " + trimHtml(description));
    }
    
    public static void assertFalse(boolean condition, String description) {	
   	try{
   	    Assert.assertFalse(condition, description);
   	}catch (AssertionError failure){
   	 logFailure("Assert False - " + description );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert False - " + trimHtml(description));
   	    Assert.fail(description);
   	}
   	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert False - " + description + "</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert False - " + trimHtml(description));
       }
    
    public static void assertEquals(Object value1, Object value2, String description) {
   	try{
   	    Assert.assertEquals(value1, value2,  description);
   	}catch (AssertionError failure){
   	 logFailure("Assert Equals - " + description );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Equals - " + trimHtml(description));
   	    Assert.fail(description);
   	}
   	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Equals - " + description + "</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Equals - " + trimHtml(description));
       }
    
    public static void assertNotEquals(Object value1, Object value2, String description) {
   	try{
   	    Assert.assertNotEquals(value1, value2,  description);
   	}catch (AssertionError failure){
   	 logFailure("Assert Not Equals - " + description );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Not Equals - " + trimHtml(description));
   	    Assert.fail(description);
   	}
   	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Not Equals - " + description + "</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Not Equals - " + trimHtml(description));
       }
    
    public static void assertGreaterThanZero(int value) {
   	try{
   	    Assert.assertTrue(value > 0);
   	}catch (AssertionError failure){
   	 logFailure("Assert Greater Than Zero - " + value );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Greater Than Zero - Assert " + value + " is greater than zero");
   	    Assert.fail("Assert " + value + " is greater than zero");
   	}
   	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Greater Than Zero - Assert " + value + " is greater than zero</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Greater Than Zero - Assert " + value + " is greater than zero");
       }
    
    public static void assertGreaterThanZero(float value) {
	assertGreaterThanZero((int)value);
    }
    
    public static void assertGreaterThanZero(double value) {
	assertGreaterThanZero((int)value);
    }
    
    public static void assertNull(Object condition, String description) {
   	try{
   	    Assert.assertNull(condition, description);
   	}catch (AssertionError failure){
   	 logFailure("Assert Null - " + description );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Null - " + trimHtml(description));
   	    Assert.fail(description);
   	}
   	Reporter.log(getTimestamp() + " <font size = 2 color=\"green\"><b><u>Assert Null - " + description + "</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Null - " + trimHtml(description));
       }
    
    public static void assertNotNull(Object condition, String description) {
   	try{
   	    Assert.assertNotNull(condition, description);
   	}catch (AssertionError failure){
   	 logFailure("Assert Not Null - " + description );
   	 if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Not Null - " + trimHtml(description));
   	    Assert.fail(description);
   	}
   	Reporter.log(getTimestamp() + "<font size = 2 color=\"green\"><b><u>Assert Not Null - " + description + "</font></u></b><br />");
   	if(getPrintToConsole()) System.out.println(getTimestamp() + "Assert Not Null - " + trimHtml(description));
       }
    
    public static void logScreenshot(WebDriver driver, String fileLocation, String slash, String runLocation) {
	File file = new File("");

	try {
		file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File(fileLocation));
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	if(runLocation.equalsIgnoreCase("remote")){
		fileLocation = fileLocation.replace("/var/lib/jenkins/jobs/OpenSandbox/jobs/Toyota-SauceLabs/workspace/", "job/OpenSandbox/job/Toyota-SauceLabs/ws/");
		//fileLocation = fileLocation.replace("/var/lib/jenkins/jobs/OpenSandbox/jobs/Toyota-SauceLabs/workspace/", "job/OpenSandbox/job/Toyota-SauceLabs/230/testngreports/toyota/TestAllSecondaryNavigations/");
		Reporter.log("<a href='https://jenkins.orasi.com/" + fileLocation + "'>FAILED SCREENSHOT</a>");
	}else{
		TestReporter.log(fileLocation);
		Reporter.log("<a href='" + fileLocation + "'> <img src='file:///" + fileLocation + "' height='200' width='300'/> </a>");
	}
    }
}
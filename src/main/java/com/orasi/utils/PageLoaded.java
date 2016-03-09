package com.orasi.utils;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.core.interfaces.Element;
import com.orasi.core.interfaces.impl.internal.ElementFactory;
import com.orasi.exception.automation.PageInitialization;
import com.orasi.utils.debugging.Highlight;

/**
 * Several different methods of waiting for a page to finish loading.
 * 
 * 
 * @version 10/16/2014
 * @author Justin Phlegar
 * 
 */
public class PageLoaded {
	private static boolean defaultSyncHandler = true;

	/**
	 * Show what the current sync handler is. True will cause the sync to throw an exception  while false will just have the element sync return a boolean
	 * 
	 * @version 1/14/2016
	 * @author Justin Phlegar
	 * @return boolean
	 */

	public static boolean getSyncToFailTest() {
	    return defaultSyncHandler;
	}

	/**
	 * Set on how to handle element sync failures. True will cause the sync to throw an exception  while false will just have the element sync return a boolean
	 * 
	 * @param defaultSyncHandler True/False 
	 * @version 1/14/2016
	 * @author Justin Phlegar
	 */
	public static void setSyncToFailTest(boolean defaultSyncHandler) {
	    PageLoaded.defaultSyncHandler = defaultSyncHandler;
	}
	
	/**
	 * This waits for a specified element on the page to be found on the page by
	 * the driver Uses the default test time out set by WebDriverSetup
	 * 
	 * @param clazz
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param oDriver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 10/16/2014
	 * @author Justin Phlegar
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isElementLoaded(Class clazz, OrasiDriver oDriver, Element obj) {		
		return isElementLoaded(clazz, oDriver, obj, oDriver.getElementTimeout());
	}

	/**
	 * Overloaded method where you can specify the timeout This waits for a
	 * specified element on the page to be found on the page by the driver
	 * 
	 * 
	 * @param clazz
	 *            the class calling this method - used so can initialize the
	 *            page class repeatedly
	 * @param oDriver
	 *            The webDriver
	 * @param obj
	 *            The element you are waiting to display on the page
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isElementLoaded(Class clazz, OrasiDriver oDriver, Element obj, int timeout) {
	   	int count = 0;
		int driverTimeout = oDriver.getElementTimeout();
		// set the timeout for looking for an element to 1 second as we are
		// doing a loop and then refreshing the elements
		oDriver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		try {

			while (!obj.elementWired()) {
			    if (count == timeout) {
				break;
			    } else {
				count++;
				initializePage(clazz, oDriver);
			    }
			}
			
		} catch (NullPointerException | NoSuchElementException | StaleElementReferenceException | PageInitialization e){
		    return false;
		} finally{
    			// set the timeout for looking for an element back to the default timeout
    			oDriver.setElementTimeout(driverTimeout, TimeUnit.SECONDS);
		}
		
		if (count < timeout) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This uses the HTML DOM readyState property to wait until a page is
	 * finished loading. It will wait for the ready state to be either
	 * 'interactive' or 'complete'.
	 *
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public static boolean isDomInteractive(OrasiDriver oDriver) {
		return isDomInteractive(oDriver, oDriver.getPageTimeout());
	}

	/**
	 * Overloaded method - gives option of specifying a timeout. This uses the
	 * HTML DOM readyState property to wait until a page is finished loading. It
	 * will wait for the ready state to be either 'interactive' or 'complete'.
	 * 
	 * @param oDriver
	 *            The webDriver
	 * @param timeout
	 *            Integer value of number seconds to wait for a page to finish
	 *            loaded before quiting
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public static boolean isDomInteractive(OrasiDriver oDriver, int timeout) {
        int count = 0;
        Object obj = null;

        do {
            // this returns a boolean
            obj = oDriver.executeJavaScript(
                    "var result = document.readyState; return (result == 'complete' || result == 'interactive');");
            if (count == timeout)
                break;
            else {
                Sleeper.sleep(500);
                count++;

            }
        } while (obj.equals(false));

        if (count < timeout * 2) {
            return true;
        } else {
            return false;
        }
	}

	/**
	 * This uses protractor method to wait until a page is ready -
	 * notifyWhenNoOutstandingRequests
	 *
	 * @version 10/16/2014
	 * @author Justin Phlegar
	 * 
	 */
	public static void isAngularComplete(OrasiDriver oDriver) {
		try {
            oDriver.executeAsyncJavaScript("var callback = arguments[arguments.length - 1];"
					+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
		} catch (WebDriverException wde) {
			TestReporter.logFailure(
					"Unable to perform Angular sync. This is most likely because the $browser service is not injected within the Angular Controller. Performing a IsDomComplete instead");
			isDomComplete(oDriver);
		}

	}


	/**
	 * A more strict version of isDomInteractive. This uses the HTML DOM
	 * readyState property to wait until a page is finished loading. It will
	 * wait for the ready state to be 'complete'.
	 * 
	 * @param oDriver
	 *            The webDriver
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public static boolean isDomComplete(OrasiDriver oDriver) {
		return isDomComplete(oDriver, oDriver.getPageTimeout());
	}

	/**
	 * Overloaded method - gives option of specifying a timeout. A more strict
	 * version of isDomInteractive This uses the HTML DOM readyState property to
	 * wait until a page is finished loading. It will wait for the ready state
	 * to be 'complete'.
	 * 
	 * @param oDriver
	 *            The webDriver
	 * @param timeout
	 *            Integer value of number seconds to wait for a page to finish
	 *            loaded before quiting
	 * @version 12/16/2014
	 * @author Jessica Marshall
	 * @return False if the element is not found after the timeout, true if is
	 *         found
	 */
	public static boolean isDomComplete(OrasiDriver oDriver, int timeout) {
        int count = 0;
        Object obj = null;
    
            do {
                // this returns a boolean
                obj = oDriver.executeJavaScript("var result = document.readyState; return (result == 'complete');");
                
                if (count == timeout)
                    break;
                else {
                    Sleeper.sleep(500);
                    count++;    
                }
                
            } while (obj.equals(false));
    
            if (count < timeout * 2) {
                return true;
            } else {
                return false;
            }
	}

	/**
	 * @summary Used to create all page objects WebElements as proxies (not
	 *          actual objects, but rather placeholders) or to reinitialize all
	 *          page object WebElements to allow for the state of a page to
	 *          change and not fail a test
	 * @return N/A
	 * @param clazz - page class that is calling this method for which to initialize elements
	 * 
	 * @param oDriver - The webDriver
	 */
	public static void initializePage(Class<?> clazz, OrasiDriver oDriver) {
		try {
			ElementFactory.initElements(oDriver, clazz.getConstructor(TestEnvironment.class));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new PageInitialization("Unable to initialize page", oDriver);
		}
	}

	/**
	 * Used in conjunction with WebElementPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out passed in
	 * parameter timeout If object is not present within the time, handle error
	 * based on returnError
	 * 
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncPresent(OrasiDriver driver, Element element,  Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		By locator = element.getElementLocator();
		TestReporter.interfaceLog("<i> Syncing to element [ <b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>PRESENT</b> in DOM within [ " + timeout + " ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementPresent(driver, locator)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout);

		if (!found && failTestOnSync) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>PRESENT</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not PRESENT on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncVisible(OrasiDriver driver, Element element,  Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>VISIBLE<b> within [ " + timeout + " ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementVisible(driver, element, timeout)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>VISIBLE</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not VISIBLE on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}
		return found;
	}


	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 * 
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncHidden(OrasiDriver driver, Element element,  Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>HIDDEN</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (!webElementVisible(driver, element, timeout)) {
				found = true;
				break;
			}
		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>HIDDEN</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not HIDDEN on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}

	/**
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out passed in the
	 * variable timeout If object is not enabled within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncEnabled(OrasiDriver driver, Element element,  Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
	    boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>ENABLED</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (webElementEnabled(driver, element)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>ENABLED</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not ENABLED on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}

	

	/**
	 * Used in conjunction with WebObjectDisabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out passed in
	 * the variable timeout If object is not disabled within the time, handle
	 * the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncDisabled(OrasiDriver driver, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to element [<b>@FindBy: " + element.getElementLocatorInfo()
				+ "</b> ] to be <b>DISABLED</b> within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);

		stopwatch.start();
		do {
			if (!webElementEnabled(driver, element)) {
				found = true;
				break;
			}
		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog("<i>Element [<b>@FindBy: " + element.getElementLocatorInfo()
					+ " </b>] is not <b>DISABLED</b> on the page after [ "
					+ (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] is not DISABLED on the page after [ "
							+ (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param text	(Required) The text the element should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncTextInElement(OrasiDriver driver, String text, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to text [<b>" + text + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementTextPresent(driver, element, text)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] did not contain the text [ " + text
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] did not contain the text [ " + text
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}


	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param regex (Required) The regular expression that should match to text the element should contain in either its text or 'value' attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncTextMatchesInElement(OrasiDriver driver, String regex, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to text regular expression [<b>" + regex + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementTextMatches(driver, element, regex)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] did not contain the text [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] did not contain the text [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param attribute - Element attribute to view
	 * @param value	(Required) The text the element attribute should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncAttributeContainsValue(OrasiDriver driver, String attribute, String value, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to attribute [<b>" + attribute + "</b> ] to contain [<b>" + value + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementAttributePresent(driver, element, attribute, value)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not contain the text [ " + value
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ]attribute [" + attribute + "] did not contain the text [ " + value
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}
	
	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param attribute - Element attribute to view
	 * @param regex	(Required) The regular expression that should match the text of the element attribute 
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncAttributeMatchesValue(OrasiDriver driver, String attribute, String regex, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to attribute [<b>" + attribute + "</b> ] to match the regular expression of [<b>" + regex + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementAttributeMatches(driver, element, attribute, regex)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] attribute [<b>" + attribute + "</b> ] did not match the regular expression of [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ]attribute [" + attribute + "] did not match the regular expression of [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param cssProperty - Element CSS Property to view
	 * @param value	(Required) The text the element attribute should contain in either its text or value attribute
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncCssContainsValue(OrasiDriver driver, String cssProperty, String value, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to CSS Property [<b>" + cssProperty + "</b> ] to contain [<b>" + value + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementCssPropertyPresent(driver, element, cssProperty, value)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty  + "</b> ] did not contain the text [ " + value
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException( "Element [ @FindBy: " + element.getElementLocatorInfo() + " ] CSS Property [" + cssProperty  + " ] did not contain the text [ " + value
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}else if(!found){
		     Highlight.highlightError(driver, element);
		}
		else {
		    if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		   }
		return found;
	}
	
	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param driver - instance of the OrasiDriver
	 * @param element - Element to sync too
	 * @param cssProperty - Element CSS Property to match
	 * @param regex	(Required) The regular expression that should match the text of the element CSS Property 
	 * @param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public static boolean syncCssMatchesValue(OrasiDriver driver, String cssProperty, String regex, Element element, Object... args) {
	    int timeout = driver.getElementTimeout();
	    boolean failTestOnSync = PageLoaded.getSyncToFailTest();
	    try{
    	    if(args[0] != null) timeout = Integer.valueOf(args[0].toString());
    	    if(args[1] != null) failTestOnSync = Boolean.parseBoolean(args[1].toString());
	    }catch(ArrayIndexOutOfBoundsException aiobe){}
		boolean found = false;
		long timeLapse;
		StopWatch stopwatch = new StopWatch();
		TestReporter.interfaceLog("<i>Syncing to CSS Property [<b>" + cssProperty + "</b> ] to contain [<b>" + regex + "</b> ] in element [<b>@FindBy: "
				+ element.getElementLocatorInfo() + "</b> ] to be displayed within [ <b>" + timeout + "</b> ] seconds.</i>");
		int currentTimeout = driver.getElementTimeout();
		driver.setElementTimeout(1, TimeUnit.MILLISECONDS);
		stopwatch.start();
		do {
			if (webElementCssPropertyMatches(driver, element, cssProperty, regex)) {
				found = true;
				break;
			}

		} while (stopwatch.getTime() / 1000.0 < (long) timeout);
		stopwatch.stop();
		timeLapse = stopwatch.getTime();
		stopwatch.reset();

		driver.setElementTimeout(currentTimeout, TimeUnit.SECONDS);
		if (!found && failTestOnSync) {
		    	Highlight.highlightError(driver, element);
			TestReporter.interfaceLog(
					"<i>Element [<b>@FindBy: " + element.getElementLocatorInfo() + " </b>] CSS Property [<b>" + cssProperty  + "</b> ] did not match the regular expression of [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.</i>");
			throw new RuntimeException(
					"Element [ @FindBy: " + element.getElementLocatorInfo() + " ] CSS Property [" + cssProperty  + "] did not match the regular expression of [ " + regex
							+ " ] after [ " + (timeLapse) / 1000.0 + " ] seconds.");
		}
		if(Highlight.getDebugMode()) Highlight.highlightSuccess(driver, element);
		return found;
	}
	
	/**
	 * Use WebDriver Wait to determine if object is present in the DOM or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param locator
	 *            {@link By} object to search for
	 * @return TRUE if element is currently present in the DOM, FALSE if the
	 *         element is not present in the DOM
	 */
	private static boolean webElementPresent(OrasiDriver driver, By locator) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}

	}

	/**
	 * Use WebDriver Wait to determine if object is visible on the screen or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @return TRUE if element is currently visible on the screen, FALSE if the
	 *         element is not visible on the screen
	 */
	private static boolean webElementVisible(OrasiDriver driver, WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);

		try {
		    if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		    return wait.until(ExtendedExpectedConditions.elementToBeVisible(element));
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		    return false;
		}
	}

	/**
	 * Use WebDriver Wait to determine if object is enabled on the screen or not
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @return TRUE if element is currently enabled on the screen, FALSE if the
	 *         element is not enabled on the screen
	 */
	private static boolean webElementEnabled(OrasiDriver driver, WebElement element) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

		try {
			if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
			return wait.until(ExpectedConditions.elementToBeClickable(element)) != null;
			// return element.isEnabled();
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			return false;
		}

	}

	/**
	 * Use WebDriver Wait to determine if object contains the expected text
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @param text
	 *            The text to search for
	 * @return TRUE if element is currently visible on the screen, FALSE if the
	 *         element is not visible on the screen
	 */
	private static boolean webElementTextPresent(OrasiDriver driver, WebElement element, String text) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);
	   // System.out.println(text);
		try {

			if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
			if (wait.until(ExpectedConditions.textToBePresentInElement(element, text)) != null) return true;
			else if (wait.until(ExpectedConditions.textToBePresentInElementValue(element, text)) != null) {
				return true;
			} else {
				return false;
			}

		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
			try {
				if (wait.until(ExpectedConditions.textToBePresentInElementValue(element, text)) != null) {
					return true;
				} else {
					return false;
				}
			} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException
					| TimeoutException e2) {
				return false;
			} catch (WebDriverException wde) {
				return false;
			}

		}
	}	
	/**
	 * Use WebDriver Wait to determine if object contains the expected text
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 *            Element to search for
	 * @param regex
	 *            The regex to match in element text
	 * @return TRUE if element text or value matches the regex
	 */
	private static boolean webElementTextMatches(OrasiDriver driver, WebElement element, String regex) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);
		try {
			if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		    if(wait.until(ExtendedExpectedConditions.textToMatchInElement(element, regex)) != null) return true;
		    else if (wait.until(ExtendedExpectedConditions.textToMatchInElementAttribute(element, "value", regex)) != null) {
			return true;
		    }else{
			return false;
		    }
		} catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		    try {
			if (wait.until(ExtendedExpectedConditions.textToMatchInElementAttribute(element, "value", regex)) != null) {
				return true;
			} else {
				return false;
			}
		    } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e2) {
        		return false;
        	    } catch (WebDriverException wde) {
        		return false;
        	    }
		}
	}
	
	/**
	 * Use WebDriverWait and custom ExpectedConditions to determine if Element attribute equals expected value
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 * 		Element to search for
	 * @param attribute
	 * 		Element attribute to validate
	 * @param value
	 * 		Value of attribute to validate
	 * @return TRUE if element attribute equals expected value
	 */
	private static boolean webElementAttributePresent(OrasiDriver driver, WebElement element, String attribute, String value) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

	    try {
		if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		return wait.until(ExtendedExpectedConditions.textToBePresentInElementAttribute(element, attribute, value));
	    } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		return false;
	    }
	}

	/**
	 * Use WebDriverWait and custom ExpectedConditions to determine if Element attribute matches expected value
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 * 		Element to search for
	 * @param attribute
	 * 		Element attribute to validate
	 * @param regex
	 * 		Regular expression of attribute to validate
	 * @return TRUE if element attribute matches regular expression of expected value
	 */
	private static boolean webElementAttributeMatches(OrasiDriver driver, WebElement element, String attribute, String regex) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

	    try {
		if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		return wait.until(ExtendedExpectedConditions.textToMatchInElementAttribute(element, attribute, regex));
	    } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		return false;
	    }
	}

	/**
	 * Use WebDriverWait and custom ExpectedConditions to determine if Element CssProperty equals expected value
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 * 		Element to search for
	 * @param CssProperty
	 * 		Element CssProperty to validate
	 * @param value
	 * 		Value of CssProperty to validate
	 * @return TRUE if element CssProperty equals expected value
	 */
	private static boolean webElementCssPropertyPresent(OrasiDriver driver, WebElement element, String cssProperty, String value) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

	    try {
		if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		return wait.until(ExtendedExpectedConditions.textToBePresentInElementCssProperty(element, cssProperty, value));
	    } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		return false;
	    }
	}

	/**
	 * Use WebDriverWait and custom ExpectedConditions to determine if Element CssProperty matches expected value
	 * 
	 * @author Justin
	 * @param driver
	 *            Main WebDriver
	 * @param element
	 * 		Element to search for
	 * @param CssProperty
	 * 		Element CssProperty to validate
	 * @param regex
	 * 		Regular expression of CssProperty to validate
	 * @return TRUE if element CssProperty matches regular expression of expected value
	 */
	private static boolean webElementCssPropertyMatches(OrasiDriver driver, WebElement element, String cssProperty, String regex) {
	    WebDriverWait wait = new WebDriverWait(driver, 0);

	    try {
		if(Highlight.getDebugMode()) Highlight.highlightDebug(driver, element);
		return wait.until(ExtendedExpectedConditions.textToMatchInElementCssProperty(element, cssProperty, regex));
	    } catch (NoSuchElementException | ClassCastException | StaleElementReferenceException | TimeoutException e) {
		return false;
	    }
	}
}
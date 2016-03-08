package com.orasi.core.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.core.Beta;
import com.orasi.core.interfaces.impl.ElementImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * wraps a web element interface with extra functionality. Anything added here
 * will be added to all descendants.
 */
@ImplementedBy(ElementImpl.class)
public interface Element extends WebElement, WrapsElement, Locatable {

	/**
	 * @author Justin
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#clear()
	 * @summary - used to clear text entry areas; has not effect on any other
	 *          elements
	 */
	@Override
	void clear();

	WebDriver getWrappedDriver();

	/**
	 * @author Justin
	 * @summary - draws the focus to an object using Actions
	 */
	void focus();

	/**
	 * @author Justin
	 * @see org.openqa.selenium.WebElement#click()
	 * @summary - default Selenium click
	 */
	@Override
	void click();

	/**
	 * @summary - click an element using a JavascriptExecutor
	 * @param driver
	 *            - Current active WebDriver object
	 */
	void jsClick();

	/**
	 * @author Justin
	 * @see org.openqa.selenium.WebElement#click()
	 * @summary - draws the focus to an object and clicks the object using
	 *          Actions
	 * @param driver
	 *            - Current active WebDriver object
	 */
	void focusClick();

	/**
	 * @author Justin
	 * @param keysToSend
	 *            - an array of characters or string literals
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#sendKeys(java.lang.CharSequence...)
	 * @summary - sends the char sequence to the element if the sequnce is not
	 *          an empty string
	 */
	@Override
	void sendKeys(CharSequence... keysToSend);

	/**
	 * @author Justin
	 * @see org.openqa.selenium.WebElement#submit
	 * @summary - submits form to remote server; exception thrown if the element
	 *          is not within a form
	 */
	@Override
	void submit();

	/**
	 * @author Justin
	 * @param by
	 *            - Search for specified {@link By} location and return it's
	 *            {@link WebElement}
	 * @return {@link WebElement}
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#findElement()
	 */
	@Override
	WebElement findElement(By by);

	/**
	 * @author Justin
	 * @param by
	 *            - Search for specified {@link By} location and return all
	 *            elements found in a {@link List}
	 * @return {@link List}
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#findElements()
	 */
	@Override
	List<WebElement> findElements(By by);

	/**
	 * @author Justin
	 * @param name
	 *            - Search for specified attribute and return it's value
	 * @return {@link String} Value of specified attribute
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getAttribute()
	 */
	@Override
	String getAttribute(String name);

	@SuppressWarnings("rawtypes")
	ArrayList getAllAttributes();

	/**
	 * @author Justin
	 * @param propertyName
	 *            - Search for specified property and return it's value
	 * @return {@link String} Value of specified property
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getCssValue()
	 */
	@Override
	String getCssValue(String propertyName);

	/**
	 * @return {@link Coordinates}
	 * @see org.orasi.chameleon.interfaces.impl.ElementImpl#getCoordinates();
	 */
	@Override
	Coordinates getCoordinates();

	/**
	 * @author Justin
	 * @return {@link Point} Return x and y location
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getLocation()
	 */
	@Override
	Point getLocation();

	/**
	 * @author Justin
	 * @return {@link Dimension} Return height and width of element
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getSize()
	 */
	@Override
	Dimension getSize();

	/**
	 * @author Justin
	 * @return {@link String} Text value in element
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getText()
	 */
	@Override
	String getText();

	/**
	 * @author Justin
	 * @return {@link String} Tag value in element
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#getTagName()
	 */
	@Override
	String getTagName();

	/**
	 * @author Justin
	 * @return {@link Boolean} Return TRUE if element is enabled, FALSE if it is
	 *         not
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#isEnabled()
	 */
	@Override
	boolean isEnabled();

	@Override
	boolean isSelected();

	/**
	 * @author Justin
	 * @return {@link Boolean} Return TRUE if element is Displayed, FALSE if it
	 *         is not
	 * @see main.java.com.orasi.core.interfaces.impl.ElementImpl#isDisplayed()
	 */
	@Override
	boolean isDisplayed();

	/**
	 * @summary - Returns true when the inner element is ready to be used.
	 * @author Justin
	 * @return boolean true for an initialized WebElement, or false if we were
	 *         somehow passed a null WebElement.
	 */
	boolean elementWired();

	/**
	 * @author Justin
	 * @return locator - type of element that was used to create element using
	 *         {@link FindBy}
	 */
	By getElementLocator();

	/**
	 * @author Justin
	 * @return {@link By} locator value of element that was used to create
	 *         element using {@link FindBy}
	 */
	String getElementIdentifier();

	/**
	 * @author Justin
	 * @return locator identifier and the value of element that was used to
	 *         create element using {@link FindBy}
	 */
	String getElementLocatorInfo();

	/**
	 * @summary - Used to highlight and element on a page
	 * @author Waits
	 * @param driver
	 * @return NA
	 */
	public void highlight();

	/**
	 * @summary - Used to highlight and element on a page
	 * @author Waits
	 * @param driver
	 *            - Current active WebDriver object
	 */
	public void scrollIntoView();

	@Beta
	@Override
	public <X> X getScreenshotAs(OutputType<X> target);


	/**
	 * Used in conjunction with WebObjectPresent to determine if the desired
	 * element is present in the DOM Will loop for the time out passed in
	 * parameter timeout If object is not present within the time, handle error
	 * based on returnError
	 * 
	 * @author Justin
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
	public boolean syncPresent(Object... args);

	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
	 *@param args
	 *  		Optional arguments </br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>timeout</b> - the maximum time in seconds the method should try to sync. Called 
	 *  							 with syncTextInElement("text", 10)</br>
	 *  		&nbsp;&nbsp;&nbsp;&nbsp;<b>failTestOnSyncFailure </b>- if TRUE, the test will throw an exception and 
	 *  					fail the script. If FALSE, the script will 
	 *  					not fail, instead a FALSE will be returned 
	 *  					to the calling function. Called with 
	 *  					syncTextInElement("text", 10, false)
	 */
	public boolean syncVisible(Object... args);


	/**
	 * Used in conjunction with WebObjectVisible to determine if the desired
	 * element is visible on the screen Will loop for the time out passed in the
	 * variable timeout If object is not visible within the time, handle the
	 * error based on the boolean
	 * 
	 * @author Justin
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
	public boolean syncHidden(Object... args);

	/**
	 * Used in conjunction with WebObjectEnabled to determine if the desired
	 * element is enabled on the screen Will loop for the time out passed in the
	 * variable timeout If object is not enabled within the time, handle the
	 * error based on the boolean
	 *
	 * @author Justin
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
	public boolean syncEnabled( Object... args);

	

	/**
	 * Used in conjunction with WebObjectDisabled to determine if the desired
	 * element is disabled on the screen Will loop for the time out passed in
	 * the variable timeout If object is not disabled within the time, handle
	 * the error based on the boolean
	 *
	 * @author Justin
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
	public boolean syncDisabled( Object... args);

	
	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param text - Text to search for
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
	public boolean syncTextInElement(String text, Object... args);
	
	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
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
	public boolean syncTextMatchesInElement(String regex, Object... args);
	

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param attribute (Required) - Element attribute to view
	 * @param value	(Required) - The text the element attribute should contain in either its text or value attribute
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
	public boolean syncAttributeContainsValue(String attribute, String value, Object... args);

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param attribute (Required) - Element attribute to view
	 * @param regex	(Required) - The regular expression that should match the text of the element attribute 
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
	public boolean syncAttributeMatchesValue(String attribute, String regex, Object... args);
	
	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param cssProperty (Required) - Element CSS Property to match
	 * @param regex	(Required) - The regular expression that should match the text of the element CSS Property 
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
	public boolean syncCssPropertyContainsValue(String cssProperty, String value, Object... args);

	/**
	 * Used in conjunction with WebObjectText Present to determine if the
	 * desired text is present in the desired element Will loop for the time out
	 * passed in the variable timeout If text is not present within the time,
	 * handle the error based on the boolean
	 *
	 * @author Justin
	 * @param cssProperty (Required) - Element CSS Property to match
	 * @param regex	(Required) - The regular expression that should match the text of the element CSS Property 
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
	public boolean syncCssPropertyMatchesValue(String cssProperty, String regex, Object... args);
}
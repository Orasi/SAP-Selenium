package com.orasi.core.interfaces.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.Textbox;
import com.orasi.utils.Base64Coder;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;

/**
 * TextInput wrapper.
 */
public class TextboxImpl extends ElementImpl implements Textbox {
	/**
	 * Creates a Element for a given WebElement.
	 * 
	 * @param element
	 *            element to wrap up
	 */
	public TextboxImpl(WebElement element) {
		super(element);
	}



	/**
	 * @summary - Gets the value of an input field. Overrides default clear().
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear() {
		try {
			getWrappedElement().clear();
			TestReporter.interfaceLog(" Clear text from Textbox [<b>@FindBy: " + getElementLocatorInfo() + " </b>]");
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog(" Clear text from Textbox [<b>@FindBy: " + getElementLocatorInfo() + " </b>]",
					true);
			throw rte;
		}
	}

	/**
	 * @summary - If the text parameter is not an empty string, this method
	 *          clears any existing values and performs a "sendKeys(text)" to
	 *          simulate typing the value. If the text parameter is an empty
	 *          string, this step is skipped.
	 * @param text
	 *            - text to enter into the field
	 */
	@Override
	public void set(String text) {
		if (!text.isEmpty()) {
			try {
				getWrappedElement().clear();
				getWrappedElement().sendKeys(text);
			} catch (RuntimeException rte) {
				TestReporter.interfaceLog("Send Keys [ <b>" + text.toString() + "</b> ] to Textbox [ <b>@FindBy: "
						+ getElementLocatorInfo() + " </b>  ]", true);
				throw rte;
			}
		} else {
			TestReporter.interfaceLog(" Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - Overloads overridden set() method. If the text parameter is
	 *          not an empty string, this method uses a JavascriptExecutor to
	 *          scroll the text field into view and click the text field, then
	 *          uses Selenium to clear any existing values and performs a
	 *          "sendKeys(text)" to simulate typing the value. If the text
	 *          parameter is an empty string, this step is skipped.
	 * @param text
	 *            - text to enter into the field
	 */
	@Override
	public void scrollAndSet(String text) {
		if (!text.isEmpty()) {
			try {
				getWrappedDriver().executeJavaScript("arguments[0].scrollIntoView(true);arguments[0].click();",
						getWrappedElement());
				getWrappedElement().clear();
				getWrappedElement().sendKeys(text);
				TestReporter.interfaceLog(" Send Keys [ <b>" + text + "</b> ] to Textbox [ <b>@FindBy: "
						+ getElementLocatorInfo() + " </b> ]");

			} catch (RuntimeException rte) {
				TestReporter.interfaceLog("Send Keys [ <b>" + text + "</b> ] to Textbox [ <b>@FindBy: "
						+ getElementLocatorInfo() + " </b> ]", true);
				throw rte;
			}
		} else {
			TestReporter
					.interfaceLog(" Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - If the text parameter is not an empty string, the text field
	 *          is clicked, then sendKeys() is used to select any/all existing
	 *          text, type the text passed in the parameter and send a "TAB"
	 *          key. This is useful if moving from an element is required to
	 *          trigger underlying JavaScript. If the text parameter is an empty
	 *          string, this step is skipped.
	 * @param text
	 *            - text to enter into the field
	 */
	@Override
	public void safeSet(String text) {
		if (!text.isEmpty()) {
			try {
				char ctrl_a = '\u0001';
				getWrappedElement().click();
				getWrappedElement().sendKeys(String.valueOf(ctrl_a));
				getWrappedElement().sendKeys(text);
				getWrappedElement().sendKeys(Keys.TAB);
				TestReporter.interfaceLog(" Send Keys [ <b>" + text.toString() + "</b> ] to Textbox [  <b>@FindBy: "
						+ getElementLocatorInfo() + " </b> ]");
			} catch (RuntimeException rte) {
				TestReporter.interfaceLog("Send Keys [ <b>" + text.toString() + "</b> ] to Textbox [  <b>@FindBy: "
						+ getElementLocatorInfo() + " </b> ]", true);
				throw rte;
			}
		} else {
			TestReporter
					.interfaceLog(" Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - If the text parameter is not an empty string, the parameter is
	 *          decoded using the Base64Coder class and a sendKeys() is used to
	 *          simulate typing the text. If the text parameter is an empty
	 *          string, this step is skipped.
	 * @param text
	 *            - text to enter into the field
	 */
	@Override
	public void setSecure(String text) {
		if (!text.isEmpty()) {
			try {
				getWrappedElement().sendKeys(Base64Coder.decodeString(text).toString());
				TestReporter.interfaceLog(" Send encoded text [ <b>" + text.toString()
						+ "</b> ] to Textbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
			} catch (RuntimeException rte) {
				TestReporter.interfaceLog("Send encoded text [ <b>" + text.toString()
						+ "</b> ] to Textbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
				throw rte;
			}
		} else {
			TestReporter
					.interfaceLog(" Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - If the text parameter is not an empty string, the text field
	 *          is clicked, then sendKeys() is used to select any/all existing
	 *          text, type the decoded parameter (decode the parameter using the
	 *          Base64Coder) in the text field and send a "TAB" key. This is
	 *          useful if moving from an element is required to trigger
	 *          underlying JavaScript. If the text parameter is an empty string,
	 *          this step is skipped.
	 * @param text
	 *            - text to enter into the field
	 */
	@Override
	public void safeSetSecure(String text) {
		if (!text.isEmpty()) {
			try {
				char ctrl_a = '\u0001';
				getWrappedElement().click();
				getWrappedElement().sendKeys(String.valueOf(ctrl_a));
				getWrappedElement().sendKeys(Base64Coder.decodeString(text).toString());
				getWrappedElement().sendKeys(Keys.TAB);
				TestReporter.log(" Send encoded text [ <b>" + text.toString() + "</b> ] to Textbox [  <b>@FindBy: "
						+ getElementLocatorInfo() + " </b> ]");
			} catch (RuntimeException rte) {
				TestReporter.interfaceLog("Send encoded text [ <b>" + text.toString()
						+ "</b> ] to Textbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
				throw rte;
			}

		} else {
			TestReporter
					.interfaceLog(" Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}

	}

	/**
	 * @summary - Gets the value of the attribute "value" of an input field.
	 * @return String with the value of the field.
	 */
	@Override
	public String getText() {
		return getWrappedElement().getAttribute("value");
	}
}
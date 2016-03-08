package com.orasi.core.interfaces.impl;

import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.Button;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;

/**
 * Wraps a label on a html form with some behavior.
 */
public class ButtonImpl extends ElementImpl implements Button {
	// private java.util.Date date= new java.util.Date();
	/**
	 * Creates a Element for a given WebElement.
	 *
	 * @param element
	 *            - element to wrap up
	 */
	public ButtonImpl(WebElement element) {
		super(element);
	}



	@Override
	public void click() {

		try {
			getWrappedElement().click();
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog("Clicked Button [ <b>@FindBy: " + getElementLocatorInfo() + "</b>]", true);
			throw rte;
		}

		TestReporter.interfaceLog("Clicked Button [ <b>@FindBy: " + getElementLocatorInfo() + "</b>]");

	}

	@Override
	public void jsClick() {

		try {
			getWrappedDriver().executeJavaScript("arguments[0].click();", element);
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog("Clicked Button [ <b>@FindBy: " + getElementLocatorInfo() + "</b>]", true);
			throw rte;
		}
		TestReporter.interfaceLog("Clicked Button [ <b>@FindBy: " + getElementLocatorInfo() + "</b>]");

	}
}
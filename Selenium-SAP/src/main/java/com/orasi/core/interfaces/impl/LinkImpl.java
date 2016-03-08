package com.orasi.core.interfaces.impl;

import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.Link;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;

/**
 * Wraps a label on a html form with some behavior.
 */
public class LinkImpl extends ElementImpl implements Link {
	/**
	 * Creates a Element for a given WebElement.
	 *
	 * @param element
	 *            element to wrap up
	 */
	public LinkImpl(WebElement element) {
		super(element);
	}


	@Override
	public void jsClick() {

		try {
			getWrappedDriver().executeJavaScript(
					"if( document.createEvent ) {var click_ev = document.createEvent('MouseEvents'); click_ev.initEvent('click', true , true )"
							+ ";arguments[0].dispatchEvent(click_ev);} else { arguments[0].click();}",
					element);
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
			throw rte;
		}
		TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");

	}

	@Override
	public void click() {
		try {
			getWrappedElement().click();
		} catch (RuntimeException rte) {
			TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]", true);
			throw rte;
		}
		TestReporter.interfaceLog(" Click Link [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
	}

	@Override
	public String getURL() {
		return getWrappedElement().getAttribute("href");
	}

}

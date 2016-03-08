package com.orasi.core.interfaces.impl;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.Listbox;
import com.orasi.exception.automation.ListboxOptionNotFoundException;
import com.orasi.utils.OrasiDriver;
import com.orasi.utils.TestReporter;

/**
 * Wrapper around a WebElement for the Select class in Selenium.
 */
public class ListboxImpl extends ElementImpl implements Listbox {
	private final org.openqa.selenium.support.ui.Select innerSelect;

	/**
	 * @summary - Wraps a WebElement with listbox functionality.
	 * @param element
	 *            - element to wrap up
	 */
	public ListboxImpl(WebElement element) {
		super(element);
		this.innerSelect = new org.openqa.selenium.support.ui.Select(element);
	}



	/**
	 * @summary - Wraps Selenium's method.
	 * @param text
	 *            - visible text to select
	 * @see org.openqa.selenium.support.ui.Select#selectByVisibleText(String)
	 */
	@Override
	public void select(String text) {
		if (!text.isEmpty()) {
			try {
				try {
					innerSelect.selectByVisibleText(text);
				} catch (RuntimeException rte) {
					TestReporter.interfaceLog("Select option [ <b>" + text.toString()
							+ "</b> ] from Listbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b>]", true);
					throw rte;
				}

				TestReporter.interfaceLog("Select option [ <b>" + text.toString()
						+ "</b> ] from Listbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b>]");
			} catch (NoSuchElementException e) {
				String optionList = "";
				List<WebElement> optionsList = innerSelect.getOptions();
				for (WebElement option : optionsList) {
					optionList += option.getText() + " | ";
				}
				TestReporter.interfaceLog(" The value of <b>[ " + text + "</b> ] was not found in Listbox [  <b>@FindBy: "
								+ getElementLocatorInfo() + " </b>]. Acceptable values are " + optionList + " ]");
				throw new ListboxOptionNotFoundException("The value of [ " + text + " ] was not found in Listbox [  @FindBy: "
						+ getElementLocatorInfo() + " ]. Acceptable values are " + optionList, getWrappedDriver().getWebDriver());
			}
		} else {
			TestReporter.interfaceLog("Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - Wraps Selenium's method.
	 * @param value
	 *            - option value to select
	 * @see org.openqa.selenium.support.ui.Select#selectByVisibleText(String)
	 */
	@Override
	public void selectValue(String value) {
		if (!value.isEmpty()) {
			try {
				try {
					innerSelect.selectByValue(value);
				} catch (RuntimeException rte) {
					TestReporter.interfaceLog("Select option [ <b>" + value.toString()
							+ "</b> ] from Listbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b>]", true);
					throw rte;
				}

				TestReporter.interfaceLog("Select option [ <b>" + value.toString()
						+ "</b> ] from Listbox [  <b>@FindBy: " + getElementLocatorInfo() + " </b>]");
			} catch (NoSuchElementException e) {
				String optionList = "";
				List<WebElement> optionsList = innerSelect.getOptions();
				for (WebElement option : optionsList) {
					optionList += option.getAttribute("value") + " | ";
				}
				TestReporter
						.interfaceLog(" The value of <b>[ " + value + "</b> ] was not found in Listbox [  <b>@FindBy: "
								+ getElementLocatorInfo() + " </b>]. Acceptable values are " + optionList + " ]");
				throw new ListboxOptionNotFoundException("The value of [ " + value + " ] was not found in Listbox [  @FindBy: "
						+ getElementLocatorInfo() + " ]. Acceptable values are " + optionList);
			}
		} else {
			TestReporter.interfaceLog("Skipping input to Textbox [ <b>@FindBy: " + getElementLocatorInfo() + " </b> ]");
		}
	}

	/**
	 * @summary - Wraps Selenium's method.
	 * @see org.openqa.selenium.support.ui.Select#deselectAll()
	 */
	@Override
	public void deselectAll() {
		innerSelect.deselectAll();
	}

	/**
	 * @summary - Wraps Selenium's method.
	 * @return list of all options in the select.
	 * @see org.openqa.selenium.support.ui.Select#getOptions()
	 */
	@Override
	public List<WebElement> getOptions() {
		return innerSelect.getOptions();
	}

	/**
	 * @summary - Wraps Selenium's method.
	 * @param text
	 *            text to deselect by visible text
	 * @see org.openqa.selenium.support.ui.Select#deselectByVisibleText(String)
	 */
	@Override
	public void deselectByVisibleText(String text) {
		innerSelect.deselectByVisibleText(text);
	}

	/**
	 * @summary - Wraps Selenium's method.
	 * @return WebElement of the first selected option.
	 * @see org.openqa.selenium.support.ui.Select#getFirstSelectedOption()
	 */
	@Override
	public WebElement getFirstSelectedOption() {
		try {
			return innerSelect.getFirstSelectedOption();
		} catch (NoSuchElementException nse) {
			return null;
		}
	}

	/**
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected(String option) {
		List<WebElement> selectedOptions = innerSelect.getAllSelectedOptions();
		for (WebElement selectOption : selectedOptions) {
			if (selectOption.getText().equals(option))
				return true;
		}
		return false;
	}

	@Override
	public List<WebElement> getAllSelectedOptions() {
		return innerSelect.getAllSelectedOptions();
	}

	@Override
	public boolean isMultiple() {
		return innerSelect.isMultiple();
	}
}
package com.orasi.core.interfaces;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.impl.ListboxImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface for a select element.
 */
@ImplementedBy(ListboxImpl.class)
public interface Listbox extends Element {
	/**
	 * @summary - Wraps Selenium's method.
	 * @param value
	 *            - the value/option to select.
	 * @see org.openqa.selenium.support.ui.Select#selectByVisibleText(String)
	 */
	void select(String value);

	/**
	 * @summary - Wraps Selenium's method.
	 * @param value
	 *            - the value/option to select.
	 * @see org.openqa.selenium.support.ui.Select#selectByValue(String)
	 */
	void selectValue(String value);

	/**
	 * @summary - Wraps Selenium's method.
	 * @see org.openqa.selenium.support.ui.Select#deselectAll()
	 */
	void deselectAll();

	/**
	 * @summary - Wraps Selenium's method.
	 * @param text
	 *            - text to deselect by visible text
	 * @see org.openqa.selenium.support.ui.Select#deselectByVisibleText(String)
	 */
	void deselectByVisibleText(String text);

	/**
	 * @author Justin
	 * @return WebElement
	 * @see org.openqa.selenium.support.ui.Select#getFirstSelectedOption()
	 */
	WebElement getFirstSelectedOption();

	/**
	 * @author Justin
	 * @return WebElement list of all options in a given listbox
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	List<WebElement> getOptions();

	/**
	 * @author Justin
	 * @return WebElement list of all selected options in a given listbox
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	List<WebElement> getAllSelectedOptions();

	/**
	 * @author Justin
	 * @return {@link boolean} TRUE if element is currently select
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	boolean isSelected(String option);

	boolean isMultiple();
}
package com.orasi.core.interfaces;

import com.orasi.core.interfaces.impl.CheckboxImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in CheckBox functionality.
 */
@ImplementedBy(CheckboxImpl.class)
public interface Checkbox extends Element {

	/**
	 * @summary - Toggle the state of the checkbox.
	 */
	void toggle();

	/**
	 * @summary - Toggle the state of the checkbox using JavascriptExecutor
	 */
	void jsToggle();

	/**
	 * @summary - Checks checkbox if unchecked.
	 */
	void check();

	/**
	 * @summary - Un-checks checkbox if checked.
	 */
	void uncheck();

	/**
	 * @summary - Check if an element is selected, and return boolean.
	 * @return true if check is checked, return false in other case
	 */
	boolean isChecked();

}
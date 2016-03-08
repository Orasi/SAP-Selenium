package com.orasi.core.interfaces;

import com.orasi.core.interfaces.impl.TextboxImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * Text field functionality.
 */
@ImplementedBy(TextboxImpl.class)
public interface Textbox extends Element {
	/**
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear();

	/**
	 * @param text
	 *            - The text to type into the field.
	 */
	void set(String text);

	/**
	 * @param text
	 *            - The text to type into the field.
	 */
	void scrollAndSet(String text);

	/**
	 * @param text
	 *            - The text to type into the field.
	 */
	void safeSet(String text);

	/**
	 * @param text
	 *            - Encoded text to decode then type in the field
	 */
	void setSecure(String text);

	/**
	 * @param text
	 *            - The text to type into the field.
	 */
	void safeSetSecure(String text);

	/**
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText();

}
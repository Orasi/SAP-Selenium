package com.orasi.core.interfaces;

import com.orasi.core.interfaces.impl.LinkImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in Link functionality.
 */
@ImplementedBy(LinkImpl.class)
public interface Link extends Element {

	/**
	 * @summary - Click the button using the default Selenium click
	 */
	@Override
	public void click();

	/**
	 * @summary - Click the link using a JavascriptExecutor click
	 */
	@Override
	public void jsClick();

	public String getURL();
}
package com.orasi.core.interfaces;

import org.openqa.selenium.WebElement;

import com.orasi.core.interfaces.impl.WebtableImpl;
import com.orasi.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in CheckBox functionality.
 */
@ImplementedBy(WebtableImpl.class)
public interface Webtable extends Element {
	/**
	 * @summary - Get the row count of the Webtable
	 */
	int getRowCount();

	/**
	 * @summary - Get the column count for the Webtable on a specified Row
	 */
	int getColumnCount(int row);

	/**
	 * @summary - Get cell data of the specified row and Column in a Webtable
	 */
	String getCellData(int row, int column);

	/**
	 * @summary - Return the Cell of the specified row and Column in a Webtable
	 */
	WebElement getCell(int row, int column);

	/**
	 * @summary - Validate text in Cell of the specified row and Column in a Webtable, Text can be a regular expression
	 */
	boolean validateCellDataMatches(int row, int column, String regex);

	/**
	 * @summary - Validate text in Cell of the specified row and Column in a Webtable, Text can be a regular expression
	 */
	boolean validateCellDataMatches(int row, int column, String regex, int timeout);

	/**
	 * @summary - Click cell in the specified row and Column in a Webtable
	 */
	void clickCell(int row, int column);

	/**
	 * @summary - Get Row number where text is found
	 */
	int getRowWithCellText(String text);

	/**
	 * @summary - Get Row number where text is found in a specific column
	 */
	int getRowWithCellText(String text, int columnPosition);

	/**
	 * @summary - Get Row number where text is found in a specific column and
	 *          starting row
	 */
	int getRowWithCellText(String text, int columnPosition, int startRow);

	/**
	 * @summary - Get Row number where text is found in a specific column and
	 *          starting row and case can be ignored
	 */
	int getRowWithCellText(String text, int columnPosition, int startRow, boolean exact);

	/**
	 * @summary - Get Column number where text is found
	 */
	int getColumnWithCellText(String text);

	/**
	 * @summary - Get Column number where text is found in a specific row
	 */
	int getColumnWithCellText(String text, int rowPosition);

}
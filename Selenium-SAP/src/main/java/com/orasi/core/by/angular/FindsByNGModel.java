package com.orasi.core.by.angular;
import org.openqa.selenium.WebElement;

import java.util.List;

	public interface FindsByNGModel {
	  WebElement findElementByNGModel(String using);
	  List<WebElement> findElementsByNGModel(String using);
	}


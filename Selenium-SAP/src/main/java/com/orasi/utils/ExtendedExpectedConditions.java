package com.orasi.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.orasi.core.interfaces.impl.ElementImpl;

public class ExtendedExpectedConditions {
	/**
    * Loop through windows for duration to see if it finds a title with exact queried text
    * @param title - Exact title of the Window to find
    * @return true once a window is found with exact title
    */
   public static ExpectedCondition<Boolean> findWindowWithTitleAndSwitchToIt(final String title){
	   return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		    	  try{
			    	  for(String handle : driver.getWindowHandles()){
			   	       	driver.switchTo().window(handle);
			   	       	if(driver.getTitle().equals(title)) return true;
			    	  }
		    	  }catch (WebDriverException e) {
		              return null;
		          }
		   	   
		    	  return false;
		      }

		      @Override
		      public String toString() {
		    	  return String.format("text ('%s') to be present in window %s", title);
		      }
		  };
   }
   
   /**
    * Loop through windows for duration to see if it finds a title that contains queried text
    * @param title - Partial title of the Window to find
    * @return WindowExistsWithTitle - Expected Condition to find window with title part
    */
   public static ExpectedCondition<Boolean> findWindowContainsTitleAndSwitchToIt(final String title){
	   return new ExpectedCondition<Boolean>() {
		      @Override
		      public Boolean apply(WebDriver driver) {
		    	  try{
			    	  for(String handle : driver.getWindowHandles()){
			    		  driver.switchTo().window(handle);
			    		  if(driver.getTitle().contains(title)) return true;
			    	  }
	    	  }catch (WebDriverException e) {
	              return null;
	          }
		   	   return false;
		      }

		      @Override
		      public String toString() {
		    	  return String.format("text ('%s') to be present in window %s", title);
		      }
		  };
   }
  
  
  /**
   * Loop through windows for duration to see if it finds a title with the matching regex pattern
   * @param regex - Regex of title of the Window to find
   * @return WindowExistsWithTitle - Expected Condition to find window with title regex
   */
  public static ExpectedCondition<Boolean> findWindowMatchesTitleAndSwitchToIt(final String regex){
	  return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	    	  try{
		    	  for(String handle : driver.getWindowHandles()){
		   	       driver.switchTo().window(handle);
		   	       if(driver.getTitle().matches(regex)) return true;
		   	   		}
	    	  }catch (WebDriverException e) {
	              return null;
	          }
	   	   return false;
	      }

	      @Override
	      public String toString() {
	    	  return String.format("regex ('%s') to be in window %s", regex);
	      }
	  };
  }
  
  /**
   * An expectation for checking if the given text is present in the specified element.
   *
   * @param element the WebElement
   * @param text to be present in the element
   * @return true once the element contains the given text
   */
  public static ExpectedCondition<Boolean> textToMatchInElement(
      final WebElement element, final String regex) {

    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        try {
          String elementText = element.getText();
          return elementText.matches(regex);
        } catch (StaleElementReferenceException e) {
            String elementText = driver.findElement(new ElementImpl(element).getElementLocator()).getAttribute(regex);
            if (elementText != null) {
	            return elementText.matches(regex);
	          } else {
	            return false;
	          }
        }
      }

      @Override
      public String toString() {
        return String.format("regex pattern ('%s') to be match in element %s", regex, element);
      }
    };
  }
  

  /**
   * An expectation for checking if the given text is present in the specified
   * elements value attribute.
   *
   * @param element the WebElement
   * @param attribute to search in
   * @param text to be present in the element's value attribute
   * @return true once the element's value attribute contains the given text
   */
  public static ExpectedCondition<Boolean> textToBePresentInElementAttribute(
      final WebElement element, final String attribute, final String text) {

    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        try {
          String elementText = element.getAttribute(attribute);
          if (elementText != null) {
            return elementText.contains(text);
          } else {
            return false;
          }
        } catch (StaleElementReferenceException e) {
            String elementText = driver.findElement(new ElementImpl(element).getElementLocator()).getAttribute(attribute);
            if (elementText != null) {
	            return elementText.matches(text);
	          } else {
	            return false;
	          }
        }
      }

      @Override
      public String toString() {
        return String.format("text ('%s') to be the value attribute ('%s') in element %s", text, attribute, element);
      }
    };
  }
  
  /**
   * An expectation for checking if the given regex matches the given element attribute.
   *
   * @param webelement to retrieve attribute
   * @param attribute to retrieve
   * @param text to be present in the attribute found by the locator
   * @return true once the regex matches given text in element attribute
   */
  public static ExpectedCondition<Boolean> textToMatchInElementAttribute(
	      final WebElement element, final String attribute, final String regex) {

	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          String elementText = element.getAttribute(attribute);
	          if (elementText != null) {
	            return elementText.matches(regex);
	          } else {
	            return false;
	          }
	        } catch (StaleElementReferenceException e) {
	            String elementText = driver.findElement(new ElementImpl(element).getElementLocator()).getAttribute(attribute);
	            if (elementText != null) {
		            return elementText.matches(regex);
		          } else {
		            return false;
		          }
	        }
	      }

	      @Override
	      public String toString() {
	        return String.format("attribute ('%s') to  match regex pattern ('%s') in element %s", attribute, regex, element);
	      }
	    };
	  }

  /**
   * An expectation for checking if the given text is present in CSS property the specified
   * elements value attribute.
   *
   * @param element the WebElement
   * @param CSS property to search in
   * @param text to be present in the element's CSS property value
   * @return true once the element's value CSS property contains the given text
   */
  public static ExpectedCondition<Boolean> textToBePresentInElementCssProperty(
      final WebElement element, final String cssProperty, final String text) {

    return new ExpectedCondition<Boolean>() {
      @Override
      public Boolean apply(WebDriver driver) {
        try {
          String elementText = element.getCssValue(cssProperty);
          if (elementText != null) {
            return elementText.contains(text);
          } else {
            return false;
          }
        } catch (StaleElementReferenceException e) {
            String elementText = driver.findElement(new ElementImpl(element).getElementLocator()).getCssValue(cssProperty);
            if (elementText != null) {
	            return elementText.matches(text);
	          } else {
	            return false;
	          }
        }
      }

      @Override
      public String toString() {
        return String.format("value ('%s') to be the value CSS Property ('%s') in element %s", text, cssProperty, element);
      }
    };
  }
  
  /**
   * An expectation for checking if the given regex matches the given element CSS Property.
   *
   * @param webelement to retrieve CSS property
   * @param cssProperty to retrieve
   * @param text to be present in the CSS property value found by the locator
   * @return true once the regex matches given text in element CSS Property
   */
  public static ExpectedCondition<Boolean> textToMatchInElementCssProperty(
	      final WebElement element, final String cssProperty, final String regex) {

	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	        try {
	          String elementText = element.getCssValue(cssProperty);
	          if (elementText != null) {
	            return elementText.matches(regex);
	          } else {
	            return false;
	          }
	        } catch (StaleElementReferenceException e) {
	            String elementText = driver.findElement(new ElementImpl(element).getElementLocator()).getCssValue(cssProperty);
	            if (elementText != null) {
		            return elementText.matches(regex);
		          } else {
		            return false;
		          }
	        }
	      }

	      @Override
	      public String toString() {
		  return String.format("value ('%s') to match regex pattern of value CSS Property ('%s') in element %s", regex, cssProperty, element);
	      }
	    };
	  }
  	public static ExpectedCondition<Boolean> elementToBeVisible(
	      final WebElement element) {

	    return new ExpectedCondition<Boolean>() {
	      @Override
	      public Boolean apply(WebDriver driver) {
	    	  try {
	  			Point location = element.getLocation();

	  			Dimension size = element.getSize();
	  			if ((location.getX() > 0 & location.getY() > 0) | (size.getHeight() > 0 & size.getWidth() > 0)) {
	  				if (element.getAttribute("hidden") != null)
	                      if (element.getAttribute("hidden").toLowerCase().equals("true")) return false;
	  				if (element.getAttribute("type") != null) {
	  					if (element.getAttribute("type").equals("hidden"))
	  						return false;
	  				}
	  				return true;
	  			} else {
	  				return false;
	  			}
	    	  	}catch (StaleElementReferenceException sere){
	    	  	    try{
        	    	  	WebElement refreshedElement =driver.findElement(new ElementImpl(element).getElementLocator());
        	    	  	Point location = refreshedElement.getLocation();
        
          			Dimension size = refreshedElement.getSize();
          			if ((location.getX() > 0 & location.getY() > 0) | (size.getHeight() > 0 & size.getWidth() > 0)) {
          				if (refreshedElement.getAttribute("hidden") != null)
          				    if (refreshedElement.getAttribute("hidden").toLowerCase().equals("true")) return false;
          				if (refreshedElement.getAttribute("type") != null) {
          					if (refreshedElement.getAttribute("type").equals("hidden"))
          						return false;
          				}
          				return true;
          			} else {
          				return false;
          			}
	    	  	    }catch(WebDriverException | ClassCastException | NullPointerException e2){
	    	  		return false;
	    	  	    }
	  		} catch (WebDriverException | ClassCastException | NullPointerException e) {
	  			return false;
	  		}
	      }

	      @Override
	      public String toString() {
	        return String.format("element ('%s') to be visible", element);
	      }
	    };
	  }
}
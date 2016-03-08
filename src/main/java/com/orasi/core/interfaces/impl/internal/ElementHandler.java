package com.orasi.core.interfaces.impl.internal;

import com.orasi.core.interfaces.Element;
import com.orasi.utils.Constants;
import com.orasi.utils.OrasiDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import static com.orasi.core.interfaces.impl.internal.ImplementedByProcessor.getWrapperClass;

/**
 * Replaces DefaultLocatingElementHandler. Simply opens it up to descendants of the WebElement interface, and other
 * mix-ins of WebElement and Locatable, etc. Saves the wrapping type for calling the constructor of the wrapped classes.
 */
public class ElementHandler  implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<?> wrappingType;
    private WebDriver driver;

    /**
     * Generates a handler to retrieve the WebElement from a locator for a given WebElement interface descendant.
     *
     * @param interfaceType Interface wrapping this class. It contains a reference the the implementation.
     * @param locator       Element locator that finds the element on a page.
     * @param <T>           type of the interface
     */
    public <T> ElementHandler(Class<T> interfaceType, ElementLocator locator, WebDriver driver) {
        this.locator = locator;
        this.driver = driver;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        this.wrappingType = getWrapperClass(interfaceType);
    }

    public <T> ElementHandler(Class<T> interfaceType, ElementLocator locator) {
        this.locator = locator;
        if (!Element.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        this.wrappingType = getWrapperClass(interfaceType);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public Object invoke(Object object, Method method, Object[] objects) throws Throwable {
    	WebElement element = null;
    
    	if(driver != null){
    	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    	}
    	try{        	        
        	element = locator.findElement();
        }catch(WebDriverException | ClassCastException e){
        	return false;
        }
    	
    	if(driver != null){
    	    if(driver instanceof OrasiDriver){
    		driver.manage().timeouts().implicitlyWait(((OrasiDriver)driver).getElementTimeout(), TimeUnit.SECONDS);
    	    }else{
    		driver.manage().timeouts().implicitlyWait(Constants.ELEMENT_TIMEOUT, TimeUnit.SECONDS);
    	    }
    	}
    	
        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        
        if ("getWrappedDriver".equals(method.getName())) {
            return driver;
        }
        
        Constructor cons = wrappingType.getConstructor(WebElement.class);
        Object thing = cons.newInstance(element);
        try {
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        } catch (WebDriverException e){
        	return false;
        }
    }
}
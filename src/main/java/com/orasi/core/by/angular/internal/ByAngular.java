package com.orasi.core.by.angular.internal;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.orasi.utils.OrasiDriver;

public class ByAngular {

    protected static JavascriptExecutor jse = null;

    public ByAngular(WebDriver jse) {
    	if (jse instanceof OrasiDriver) ByAngular.jse = (JavascriptExecutor) ((OrasiDriver) jse).getWebDriver();
    	else ByAngular.jse = (JavascriptExecutor)jse;
    }

    public static ByAngularRepeater repeater(String repeater) {
        return new ByAngularRepeater(jse, repeater);
    }

    public static ByAngularBinding binding(String binding) {
        return new ByAngularBinding(jse, binding);
    }
    
    public static ByAngularModel model(String model) {
        return new ByAngularModel(jse, model);
    }
    
    public static ByAngularController controller(String controller) {
        return new ByAngularController(jse, controller);
    }
    
    public static ByAngularShow show(String show) {
        return new ByAngularShow(jse, show);
    }
    
    public static ByAngularButtonText buttonText(String text) {
        return new ByAngularButtonText(jse, text);
    }


    public abstract static class BaseBy extends By {

        protected final JavascriptExecutor jse;

        public BaseBy(JavascriptExecutor jse) {
            this.jse = jse;
        }

        @SuppressWarnings("rawtypes")
		protected final void errorIfNull(Object o) {
            if (o == null || o instanceof List && ((List) o).size() == 0) {
                throw new NoSuchElementException(this + " didn't have any matching elements at this place in the DOM");
            }
        }

    }
}

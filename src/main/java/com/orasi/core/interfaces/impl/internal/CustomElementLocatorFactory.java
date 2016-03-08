package com.orasi.core.interfaces.impl.internal;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.orasi.core.by.angular.AngularElementLocator;
import com.orasi.core.by.angular.FindByNG;

public class CustomElementLocatorFactory implements ElementLocatorFactory {
	  private final WebDriver driver;
	  public CustomElementLocatorFactory(final WebDriver driver) {
	    this.driver = driver;
	  }
	  
	  @Override
	  public ElementLocator createLocator(final Field field) {
	    if (field.isAnnotationPresent(FindByNG.class)) {
	      return new AngularElementLocator(driver, field);
	    } else {
	      return new DefaultElementLocator(driver, field);
	    }
	  }
	}
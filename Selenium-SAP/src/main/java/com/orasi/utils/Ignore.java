package com.orasi.utils;

import org.openqa.selenium.Platform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Ignore {

  public static enum Driver {
    ALL,
    CHROME,
    HTMLUNIT,
    FIREFOX,
    IE,
    MARIONETTE,
    PHANTOMJS,
    REMOTE,
    SAFARI,
    ANDROID,
    IOS,
  }

  Driver[] value() default {Driver.ALL};

  Platform[] platforms() default {Platform.ANY};

  String reason() default ("Not implemented in driver yet");

  int[] issues() default {};

}
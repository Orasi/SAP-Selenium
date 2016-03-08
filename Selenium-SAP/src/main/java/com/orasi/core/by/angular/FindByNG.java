package com.orasi.core.by.angular;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.orasi.core.Beta;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindByNG {
  HowNG howNG() default HowNG.ID;
  String using() default "";
  String ngButtonText() default "";
  String ngController() default "";
  String ngModel() default "";
  String ngRepeater() default "";
  @Beta
  String ngShow() default "";
}

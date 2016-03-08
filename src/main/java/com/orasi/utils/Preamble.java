/**
 * 
 */
package com.orasi.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author justin.phlegar@orasi.com
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
public @interface Preamble {
    String author();
    String date();
    String summary();
    String precondition() default "N/A";
    String dataFile() default "N/A";
    String[] params() default "N/A";
    String returns() default "N/A";
    String[] canThrow() default "N/A";
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    String[] reviewers();
}

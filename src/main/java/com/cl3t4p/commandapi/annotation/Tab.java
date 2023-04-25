package com.cl3t4p.commandapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for the autocompletition of the argument
 *
 * @author cl3t4p
 *
 * @version 0.7
 *
 * @since 0.7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tab {
    String value();

    boolean isMethod() default false;
}

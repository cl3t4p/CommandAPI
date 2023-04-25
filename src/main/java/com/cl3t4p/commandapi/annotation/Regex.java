package com.cl3t4p.commandapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a argument to be checked with a regex
 *
 * @author cl3t4p
 *
 * @version 0.7
 *
 * @since 0.7
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regex {
    String value();
}

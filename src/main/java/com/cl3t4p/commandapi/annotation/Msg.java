package com.cl3t4p.commandapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Used to specify if the argument had an error during the parsing.
 * @author cl3t4p
 * @version 0.2
 * @since 0.2
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Msg {
    String value();
}

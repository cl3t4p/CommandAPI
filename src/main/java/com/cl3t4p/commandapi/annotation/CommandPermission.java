package com.cl3t4p.commandapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to specify the permission required to execute a command.
 *
 * @author cl3t4p
 *
 * @version 0.3
 *
 * @since 0.2
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermission {
    String value();
}

package com.cl3t4p.commandapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a method as a command.
 *
 * @author cl3t4p
 *
 * @version 0.3
 *
 * @since 0.2
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * Minimum number of arguments required.
     */
    int required() default 0;

    /**
     * Name of the command. If not specified, the method name is used.
     */
    String name() default "";

    /**
     * Aliases of the command.
     */
    String[] alias() default {};

}

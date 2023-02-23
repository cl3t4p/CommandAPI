package com.cl3t4p.commandapi.exception;

/**
 * This exception is thrown when a command cannot be created.
 * @author cl3t4p
 * @version 0.2
 * @since 0.2
 */
public class CommandCreationException extends Exception {
    public CommandCreationException(String message) {
        super(message);
    }
}

package com.cl3t4p.commandapi.exception;

/**
 * This exception is thrown when a command cannot be created.
 *
 * @author cl3t4p
 *
 * @version 0.3
 *
 * @since 0.2
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}

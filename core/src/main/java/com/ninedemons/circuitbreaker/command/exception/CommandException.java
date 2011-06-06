package com.ninedemons.circuitbreaker.command.exception;

/**
 * Signals an error within a command
 */
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

}

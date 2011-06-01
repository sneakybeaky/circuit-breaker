package com.ft.circuitbreaker.command;

import com.ft.circuitbreaker.command.exception.CommandException;

/**
 * Used to represent and encapsulate all the information needed to call a method at a later time
 */
public interface Command {

    /**
     * Perform some action.
     * @throws CommandException if an error occurs
     */
    public void execute() throws CommandException;
}

package com.ninedemons.circuitbreaker.command;

import com.ninedemons.circuitbreaker.command.exception.CommandException;

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

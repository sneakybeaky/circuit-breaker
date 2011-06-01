package com.ft.circuitbreaker;

import com.ft.circuitbreaker.command.Command;

/**
 * Wraps dangerous operations that are circumvented when the system
 * is not healthy. This differs from retries, in that circuit breakers
 * exist to prevent operations rather than re-execute them.
 *
 * The breaker can be in one of three states at any time
 * <ul>
 * <li> Closed - (normal mode) all operations are attempted
 * <li> Open - (failure mode) no operations are attempted
 * <li> Half Open - (recovery mode) One operation is attempted.
 * On success move to Closed state, on failure to Open state.
 * </ul>
 *
 */

public interface CircuitBreaker {

    /**
     * Perform the dangerous operation.
     *
     * @param command encapsulates the operation to perform
     * @return the command after it has been executed
     * @throws Exception if an error occurs in the command, or if the circuit breaker is in open state
     */
    Command doOperation(Command command) throws Exception;

    /**
     * Forces the circuit breaker open (failure mode).
     * No operations are attempted
     */
    void trip();

    /**
     * Sets the circuit breaker into half open state.
     * The next request is attempted. If it works the circuit breaker
     * is set to closed state, otherwise it is set back to open state.
     */
    void attemptReset();

    /**
     * Sets the circuit breaker into closed state - normal mode of operation.
     * All requests are passed through.
     */
    void reset();
}

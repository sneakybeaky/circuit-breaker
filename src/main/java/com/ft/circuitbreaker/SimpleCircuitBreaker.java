package com.ft.circuitbreaker;

import com.ft.circuitbreaker.clock.Clock;
import com.ft.circuitbreaker.clock.SystemClock;
import com.ft.circuitbreaker.command.Command;
import com.ft.circuitbreaker.state.*;

/**
 * Default implementation that uses consecutive failures (a failure being an Exception)
 * to trip the breaker.
 *
 * N.B. The <a href=#initialise>initialise</a> method must be called before the breaker is used.
 */
public class SimpleCircuitBreaker implements CircuitBreaker {


    private StateFactory stateFactory;

    private State state;

    public Command doOperation(final Command command) throws Exception {

        try {
            getState().preInvoke(this);
            command.execute();
            getState().postInvoke(this);

        } catch (Exception e) {
            getState().onError(this, e);
            throw e;
        }
        return command;
    }

    private State getState() {
        return state;
    }


    public void trip() {
        OpenState openState = stateFactory.createOpenState();
        openState.trip();
        this.state = openState;
    }


    public void attemptReset() {
        this.state = stateFactory.createHalfOpenState();
    }


    public void reset() {
        this.state = stateFactory.createClosedState();
    }

    /**
     * Must be called before first use
     */
    public void initialise() {
        this.state = stateFactory.createClosedState();
    }

    public void setStateFactory(StateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }
}

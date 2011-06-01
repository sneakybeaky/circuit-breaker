package com.ft.circuitbreaker.state;

import com.ft.circuitbreaker.CircuitBreaker;
import com.ft.circuitbreaker.clock.Clock;
import com.ft.circuitbreaker.clock.SystemClock;
import com.ft.circuitbreaker.exception.CircuitBreakerException;
import com.ft.circuitbreaker.exception.CircuitBreakerOpenException;

/**
 * The open state doesn't send any requests until a time threshold is passed.
 */
public class OpenState implements State {

    private Clock clock = new SystemClock();

    /**
     * Time stamp of when we tripped open
     */
    long tripTime;

    /**
     * How long we wait until we can try again
     */
    long timeoutInMillisecs = 0;

    /**
     * Called before the dangerous operation is invoked.
     *
     * If the timeout period has elapsed attempt a reset
     *
     * @param circuitBreaker the circuit breaker
     * @throws CircuitBreakerOpenException if we are still in the timeout period
     */
    public void preInvoke(CircuitBreaker circuitBreaker) throws CircuitBreakerException {
        long elapsedTime = clock.getNow() - tripTime;

        if (elapsedTime > timeoutInMillisecs) {
            circuitBreaker.attemptReset();
        } else {
            throw new CircuitBreakerOpenException("Circuit breaker is open - calls are failing fast");
        }

    }

    public void postInvoke(CircuitBreaker circuitBreaker) {
        //No-op
    }

    public void onError(CircuitBreaker circuitBreaker, Throwable throwable) {
        //No-op
    }

    /**
     * Indicates that the circuit breaker has tripped
     */
    public void trip() {
        tripTime = clock.getNow();
    }

    public void setTimeoutInMillisecs(long timeoutInMillisecs) {
        this.timeoutInMillisecs = timeoutInMillisecs;
    }


    public void setClock(Clock clock) {
        this.clock = clock;
    }

}

package com.ninedemons.circuitbreaker.state;

import com.ninedemons.circuitbreaker.CircuitBreaker;
import com.ninedemons.circuitbreaker.exception.CircuitBreakerException;

/**
 * In this state a connection is attempted, and if it succeeds the breaker is reset. If it fails
 * the breaker is set to open again.
 */
public class HalfOpenState implements State {
    public void preInvoke(CircuitBreaker circuitBreaker) throws CircuitBreakerException {
        //No-op
    }

    public void postInvoke(CircuitBreaker circuitBreaker) {
        circuitBreaker.reset();
    }

    public void onError(CircuitBreaker circuitBreaker, Throwable throwable) {
        circuitBreaker.trip();
    }
}

package com.ft.circuitbreaker.state;

import com.ft.circuitbreaker.CircuitBreaker;
import com.ft.circuitbreaker.exception.CircuitBreakerException;


/**
 * Represents a state of the circuit breaker
 */
public interface State {

    /**
     * Called before the integration point is invoked
     * @param circuitBreaker
     */
    void preInvoke(CircuitBreaker circuitBreaker) throws CircuitBreakerException;

    /**
     * Called after a successful invocation of
     * the integration point
     * @param circuitBreaker
     */
    void postInvoke(CircuitBreaker circuitBreaker);

    /**
     * Called when an error occurs when invoking the integration point
     * @param circuitBreaker
     * @param throwable
     */
    void onError(CircuitBreaker circuitBreaker, Throwable throwable);
}

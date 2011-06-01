package com.ninedemons.circuitbreaker.state;

import com.ninedemons.circuitbreaker.CircuitBreaker;
import com.ninedemons.circuitbreaker.exception.CircuitBreakerException;


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

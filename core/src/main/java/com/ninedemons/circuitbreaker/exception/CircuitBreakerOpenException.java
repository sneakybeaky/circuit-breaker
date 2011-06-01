package com.ninedemons.circuitbreaker.exception;

/**
 * Indicates that an operation was attempted while the circuit breaker is open
 */
public class CircuitBreakerOpenException extends CircuitBreakerException {
    public CircuitBreakerOpenException(String message) {
        super(message);
    }
}

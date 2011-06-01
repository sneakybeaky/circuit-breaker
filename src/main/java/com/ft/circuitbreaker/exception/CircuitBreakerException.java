package com.ft.circuitbreaker.exception;

/**
 * Indicates an error in the circuit breaker
 */
public class CircuitBreakerException extends Exception {
    public CircuitBreakerException(String message) {
        super(message);
    }

}

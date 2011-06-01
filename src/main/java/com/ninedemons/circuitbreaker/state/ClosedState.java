package com.ninedemons.circuitbreaker.state;

import com.ninedemons.circuitbreaker.CircuitBreaker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Closed state is when the integration is working normally - all
 * requests are passed down.
 */
public class ClosedState implements State {

    int failureThreshold;
    AtomicInteger failureCount = new AtomicInteger(0);

    public void preInvoke(CircuitBreaker circuitBreaker) {
        //Do nothing
    }

    public void postInvoke(CircuitBreaker circuitBreaker) {
        resetFailureCount();
    }

    private void resetFailureCount() {
        failureCount.set(0);
    }

    public void onError(CircuitBreaker circuitBreaker, Throwable throwable) {
        int currentCount = failureCount.incrementAndGet();

        if (currentCount >= failureThreshold) {
            circuitBreaker.trip();
        }
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }
}

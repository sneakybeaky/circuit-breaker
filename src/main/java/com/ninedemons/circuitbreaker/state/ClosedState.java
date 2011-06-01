package com.ninedemons.circuitbreaker.state;

import EDU.oswego.cs.dl.util.concurrent.SynchronizedInt;
import com.ninedemons.circuitbreaker.CircuitBreaker;

/**
 * Closed state is when the integration is working normally - all
 * requests are passed down.
 */
public class ClosedState implements State {

    int failureThreshold;
    SynchronizedInt failureCount = new SynchronizedInt(0);

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
        int currentCount = failureCount.increment();

        if (currentCount >= failureThreshold) {
            circuitBreaker.trip();
        }
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }
}

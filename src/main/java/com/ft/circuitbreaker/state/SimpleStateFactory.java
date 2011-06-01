package com.ft.circuitbreaker.state;


import com.ft.circuitbreaker.clock.Clock;
import com.ft.circuitbreaker.clock.SystemClock;

public class SimpleStateFactory implements StateFactory {

    /**
     * 5 minutes
     */
    public static int DEFAULT_TIMEOUT = 1000 * 60 * 5;

    private Clock clock = new SystemClock();
    private long timeoutInMillisecs = DEFAULT_TIMEOUT;
    private int failureThreshold = Integer.MAX_VALUE;


    public OpenState createOpenState() {
        OpenState openState = new OpenState();
        openState.setClock(clock);
        openState.setTimeoutInMillisecs(timeoutInMillisecs);
        return openState;
    }

    public ClosedState createClosedState() {
        ClosedState closedState = new  ClosedState();
        closedState.setFailureThreshold(failureThreshold);
        return closedState;
    }

    public HalfOpenState createHalfOpenState() {
        return new HalfOpenState();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setTimeoutInMillisecs(long timeoutInMillisecs) {
        this.timeoutInMillisecs = timeoutInMillisecs;
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = failureThreshold;
    }
}

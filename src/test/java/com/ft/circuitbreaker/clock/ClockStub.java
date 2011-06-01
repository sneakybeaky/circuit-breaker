package com.ft.circuitbreaker.clock;

/**
 * Implementation used for testing
 */
public class ClockStub implements Clock {

    long now;

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
}

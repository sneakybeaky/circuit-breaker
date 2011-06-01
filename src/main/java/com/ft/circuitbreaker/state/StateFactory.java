package com.ft.circuitbreaker.state;

/**
 * A factory knows how to create new instances of State
 */
public interface StateFactory {

    public OpenState createOpenState();

    public ClosedState createClosedState();

    public HalfOpenState createHalfOpenState();

}

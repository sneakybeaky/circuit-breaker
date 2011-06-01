package com.ninedemons.circuitbreaker.state;

import com.ninedemons.circuitbreaker.CircuitBreaker;
import com.ninedemons.circuitbreaker.clock.ClockStub;
import com.ninedemons.circuitbreaker.exception.CircuitBreakerOpenException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


public class OpenStateTest extends MockObjectTestCase {

    OpenState underTest;
    ClockStub clockStub = new ClockStub();
    long TEST_TIMEOUT_IN_MILLISECS = 10000;

    Mock mockCircuitBreaker;
    CircuitBreaker circuitBreaker;

    public void setUp() throws Exception {
        clockStub.setNow(System.currentTimeMillis());

        SimpleStateFactory simpleStateFactory = new SimpleStateFactory();
        simpleStateFactory.setClock(clockStub);
        simpleStateFactory.setTimeoutInMillisecs(TEST_TIMEOUT_IN_MILLISECS);
        underTest = simpleStateFactory.createOpenState();

        mockCircuitBreaker = mock(CircuitBreaker.class);
        circuitBreaker = (CircuitBreaker) mockCircuitBreaker.proxy();


    }

    public void testOpenStillInTimeout() throws Exception {
        underTest.trip();

        boolean exception = false;

        try {
            underTest.preInvoke(circuitBreaker);
        } catch (CircuitBreakerOpenException e) {
            exception = true;
        }

        assertTrue("Open state still within timeout period - no requests should be passed through", exception);
    }

    public void testOpenStillJustInTimeout() throws Exception {
        underTest.trip();

        setSystemTimeToJustWithinTimeout();

        boolean exception = false;

        try {
            underTest.preInvoke(circuitBreaker);
        } catch (CircuitBreakerOpenException e) {
            exception = true;
        }

        assertTrue("Open state still within timeout period - no requests should be passed through", exception);
    }

    public void testTimeoutPeriodPassed() throws Exception {

        underTest.trip();
        mockCircuitBreaker.expects(once()).method("attemptReset");

        setSystemTimeToJustAfterTimeout();

        underTest.preInvoke(circuitBreaker);

    }

    public void testPostInvokeDoesNothing() {
        underTest.postInvoke(circuitBreaker);
    }

    public void testOnErrorDoesNothing() {
        underTest.onError(circuitBreaker,new Exception());
    }

    private void setSystemTimeToJustAfterTimeout() {
        long now = clockStub.getNow();
        now += (TEST_TIMEOUT_IN_MILLISECS + 1);
        clockStub.setNow(now);
    }


    private void setSystemTimeToJustWithinTimeout() {
        long now = clockStub.getNow();
        now += (TEST_TIMEOUT_IN_MILLISECS - 1);
        clockStub.setNow(now);
    }

}

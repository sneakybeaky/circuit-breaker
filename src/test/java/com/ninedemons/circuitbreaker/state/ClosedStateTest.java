package com.ninedemons.circuitbreaker.state;

import com.ninedemons.circuitbreaker.CircuitBreaker;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


public class ClosedStateTest extends MockObjectTestCase {

    ClosedState underTest;
    int FAILURE_THRESHOLD = 10;
    Mock mockCircuitBreaker;
    CircuitBreaker circuitBreaker;

    public void setUp() {

        SimpleStateFactory simpleStateFactory = new SimpleStateFactory();
        simpleStateFactory.setFailureThreshold(FAILURE_THRESHOLD);

        underTest = simpleStateFactory.createClosedState();
        mockCircuitBreaker = mock(CircuitBreaker.class);
        circuitBreaker = (CircuitBreaker) mockCircuitBreaker.proxy();
    }

    public void testFailuresJustBelowThreshold() {

        for (int i=0; i < FAILURE_THRESHOLD-1; i++) {
            underTest.onError(circuitBreaker,null);
        }

    }

    public void testFailuresOnThreshold() {

        mockCircuitBreaker.expects(once()).method("trip");

        for (int i=0; i < FAILURE_THRESHOLD; i++) {
            underTest.onError(circuitBreaker,null);
        }

    }

    public void testOneSuccessResetsFailureCount() {

        for (int i=0; i < FAILURE_THRESHOLD-1; i++) {
            underTest.onError(circuitBreaker,null);
        }

        underTest.postInvoke(null);

        for (int i=0; i < FAILURE_THRESHOLD-1; i++) {
            underTest.onError(circuitBreaker,null);
        }

    }

    public void testPreInvokeDoesNothing() {
        underTest.preInvoke(circuitBreaker);
    }

}

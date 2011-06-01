package com.ft.circuitbreaker.state;

import com.ft.circuitbreaker.CircuitBreaker;
import com.ft.circuitbreaker.exception.CircuitBreakerException;
import junit.framework.TestCase;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;


public class HalfOpenStateTest extends MockObjectTestCase {

    HalfOpenState underTest;

    Mock mockCircuitBreaker;
    CircuitBreaker circuitBreaker;

    public void setUp() {
        SimpleStateFactory simpleStateFactory = new SimpleStateFactory();

        underTest = simpleStateFactory.createHalfOpenState();
        mockCircuitBreaker = mock(CircuitBreaker.class);
        circuitBreaker = (CircuitBreaker) mockCircuitBreaker.proxy();
    }

    public void testConnectionIsOkNow() {
        mockCircuitBreaker.expects(once()).method("reset");
        underTest.postInvoke(circuitBreaker);
    }

    public void testConnectionStillFailing() {
        mockCircuitBreaker.expects(once()).method("trip");
        underTest.onError(circuitBreaker,null);
    }

    public void testPreInvokeDoesNothing() throws CircuitBreakerException {
        underTest.preInvoke(circuitBreaker);
    }

}

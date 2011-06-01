package com.ninedemons.circuitbreaker;

import com.ninedemons.circuitbreaker.clock.ClockStub;
import com.ninedemons.circuitbreaker.command.Command;
import com.ninedemons.circuitbreaker.command.exception.CommandException;
import com.ninedemons.circuitbreaker.exception.CircuitBreakerOpenException;
import com.ninedemons.circuitbreaker.state.SimpleStateFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * Test the correctness of the circuit breaker implementation
 */
public class SimpleCircuitBreakerTest extends MockObjectTestCase {

    private static final int FAILURE_THRESHOLD = 10;
    SimpleCircuitBreaker underTest;

    ClockStub clockStub = new ClockStub();
    long TEST_TIMEOUT_IN_MILLISECS = 10000;

    Command command;
    Mock mockCommand;


    public void setUp() {
        clockStub.setNow(System.currentTimeMillis());

        SimpleStateFactory simpleStateFactory = new SimpleStateFactory();
        simpleStateFactory.setClock(clockStub);
        simpleStateFactory.setTimeoutInMillisecs(TEST_TIMEOUT_IN_MILLISECS);
        simpleStateFactory.setFailureThreshold(FAILURE_THRESHOLD);

        underTest = new SimpleCircuitBreaker();
        underTest.setStateFactory(simpleStateFactory);
        underTest.initialise();

        mockCommand = mock(Command.class);
        command = (Command) mockCommand.proxy();

    }

    public void testDefaultOperation() throws Exception {

        mockCommand.expects(once()).method("execute");
        underTest.doOperation(command);
    }

    public void testBreakerTripsAfterThresholdPassed() throws Exception {

        tripBreaker();

        boolean exception = false;

        try {
            underTest.doOperation(command);
        } catch (CircuitBreakerOpenException e) {
            exception=true;
        }

        assertTrue("Circuit breaker should have tripped and so should be failing fast",exception);
    }

    public void testBreakerResetsAfterTimeoutPassedWithSuccessfulCommand() throws Exception {

        tripBreaker();
        setTimeoutPassed();

        // Now pass in a working command
        mockCommand.expects(atLeastOnce()).method("execute");
        underTest.doOperation(command);

        // Circuit breaker should now be in closed state
        underTest.doOperation(command);
    }

    public void testBreakerTripsAfterTimeoutPassedWithBadCommand() throws Exception {

        tripBreaker();
        setTimeoutPassed();

        // Now pass in a bad command
        mockCommand.expects(atLeastOnce()).method("execute").
                will(onConsecutiveCalls(throwException(new CommandException()),returnValue(null)));

        try {
            underTest.doOperation(command);
        } catch (Exception e) {
            // ignore
        }

        // Circuit breaker should now be in open state
        boolean exception = false;

        try {
            underTest.doOperation(command);
        } catch (CircuitBreakerOpenException e) {
            exception=true;
        }

        assertTrue("Circuit breaker should have tripped and so should be failing fast",exception);
    }

    private void setTimeoutPassed() {
        long now = clockStub.getNow();
        now += TEST_TIMEOUT_IN_MILLISECS + 1;
        clockStub.setNow(now);
    }

    /**
     * Sets the breaker into tripped state by sending commands that will fail
     */
    private void tripBreaker() {
        CommandException commandException = new CommandException();

        Mock badCommand = mock(Command.class,"bad command");

        badCommand.expects(atLeastOnce()).method("execute").will(throwException(commandException));

        for (int i = 0; i < FAILURE_THRESHOLD; i++) {

            try {
                underTest.doOperation((Command)badCommand.proxy());
            } catch (Exception e) {
                assertEquals(commandException, e);
            }
        }
    }

}

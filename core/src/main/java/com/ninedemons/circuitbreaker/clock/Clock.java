package com.ninedemons.circuitbreaker.clock;

/**
 * Simple interface to enable testing of time related logic
 */
public interface Clock {

	/**
	 * Gets the current time in milliseconds
	 * 
	 * @return the difference, measured in milliseconds, between the current
	 *         time and midnight, January 1, 1970 UTC.
	 * 
	 * @see java.lang.System#currentTimeMillis
	 */
	long getNow();
}

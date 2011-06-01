package com.ft.drm.circuitbreaker.clock;

/**
 * Pass through implementation to system time
 * 
 * @see System#currentTimeMillis()
 */
public class SystemClock implements Clock {

	public long getNow() {
		return System.currentTimeMillis();
	}
}

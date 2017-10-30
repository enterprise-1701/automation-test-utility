package com.cubic.genericutils;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Contains several useful methods related to Time, It cannot be instantiated.
 * 
 * @since 1.0
 */
public abstract class TimeUtil {

	/**
	 * Returns time difference.
	 * 
	 * @param startTime statTime should be java.time.Instant as parameter
	 * @param endTime statTime should be java.time.Instant as parameter
	 * @return java.lang.String return time difference
	 */
	public static synchronized String getTimeDifference(Instant startTime, Instant endTime) {
		Duration timeElapsed = Duration.between(startTime, endTime);
		long timeDiffInMillis = timeElapsed.toMillis();
		return DurationFormatUtils.formatDuration(timeDiffInMillis, "HH:mm:ss:SSS", true);
	}

	/**
	 * Returns time difference in Millis
	 * 
	 * @param startTime statTime should be java.time.Instant as parameter
	 * @param endTime statTime should be java.time.Instant as parameter
	 * @return long value indicates time difference in Milliseconds  
	 */
	public static synchronized long getTimeDifferenceInMillis(Instant startTime, Instant endTime) {
		Duration timeElapsed = Duration.between(startTime, endTime);
		long timeDiffInMillis = timeElapsed.toMillis();
		return timeDiffInMillis;
	}

	/**
	 * Converts the time in milliseconds to time format hh:mm:ss:SSS
	 * @param value of time in milliseconds
	 * @return value indicating in time format (HH:mm:ss:SSS)
	 */
	public static synchronized String convertMilliSecondsToTimeFormat(long value) {
		return DurationFormatUtils.formatDuration(value, "HH:mm:ss:SSS", true);
	}

	/**
	 * Gives the Current time
	 * 
	 * @return value indicates current time stamp (eg:2017-06-23T14:12:42.514Z)
	 */
	public static synchronized Instant getCurrentInstant() {
		return Instant.now();
	}
	
}

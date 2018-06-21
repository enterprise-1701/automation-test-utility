package com.cubic.genericutils;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cubic.logutils.Log4jUtil;
import com.google.common.base.Stopwatch;

/**
 * A utility class which allows for easy timing of tests. A target time can be set, which is useful if you are timing tests which
 * are expected to complete in a known amount of time. This outputs a boolean value for easy use with test assert methods.
 * 
 * Other methods expected within a timer are possible, as well as output in MS or Seconds.
 * @author Nathan Hendry
 *
 */
public class TimingUtil {
	private static final Logger LOG =  Logger.getLogger(FileUtil.class.getName());
	
	private Stopwatch timer;
	private long targetTime;
	

	/**
	 * Constructor - Used when no target time is needed at creation. 
	 */
	public TimingUtil() {
		this.timer = Stopwatch.createUnstarted();
		this.targetTime = 0;
	}
	
	/**
	 * Constructor - Set a target time on creation.
	 * @param targetTimeMS Target time in Milliseconds
	 */
	public TimingUtil(long targetTimeMS) {
		this.timer = Stopwatch.createUnstarted();
		this.targetTime = targetTimeMS;
	}
	
	/**
	 * Starts the timer.
	 */
	public void start() {	
		try {
			this.timer.start();
		} catch(Exception e) {
			LOG.error("Timer failed to start");
			LOG.error(Log4jUtil.getStackTrace(e));
		}
	}
	
	/**
	 * Stops the timer.
	 */
	public void stop() {
		try {
			this.timer.stop();
		} catch(Exception e) {
			LOG.error("Timer failed to stop");
			LOG.error(Log4jUtil.getStackTrace(e));
		}
	}
	
	/**
	 * Resets timer to 0.
	 */
	public void reset() {
		try {
			this.timer.reset();
			
			boolean timerReset = (this.timer.elapsed(TimeUnit.MILLISECONDS) == 0) ? true : false;
			if (!timerReset)
				throw new Exception();
		}catch(Exception e) {
			LOG.error("Timer failed to reset");
			LOG.error(Log4jUtil.getStackTrace(e));
		}
	}
	
	/**
	 * Checks if timer has met or exceeded the target time.
	 * @return Returns True if current time < target time.
	 */
	public boolean isLessThanTargetTime() {		
		long currentTime = this.timer.elapsed(TimeUnit.MILLISECONDS);
		
		if(currentTime < this.targetTime) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the target time.
	 * @return Target time in MS
	 */
	public long getTargetTime() {
		return this.targetTime;
	}
	
	/**
	 * Sets the target time in MS
	 * @param timeMS expected target time in MS
	 */
	public void setTargetTime(long timeMS) {
		this.targetTime = timeMS;
	}
	
	/**
	 * Gets the current time on the timer in Milliseconds
	 * @return Current Timer in Milliseconds
	 */
	public long getTimeMS() {
		long currentTime = this.timer.elapsed(TimeUnit.MILLISECONDS);
		
		return currentTime;
	}
	
	/**
	 * Gets the current time on the timer in Seconds (Rounded down)
	 * @return Current Timer in Seconds
	 */
	public long getTimeSec() {
		long currentTime = this.timer.elapsed(TimeUnit.SECONDS);
		
		return currentTime;
	}
	
}

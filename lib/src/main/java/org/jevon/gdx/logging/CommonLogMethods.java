package org.jevon.gdx.logging;

import org.jevon.gdx.logging.FastLogger.PrintTimeOption;

/**
 * Common logging methods and implementations
 * for both {@link FastLogger}s and {@link GdxLog}s.
 */
public interface CommonLogMethods {

	/** 
	 * @return true if debug or higher messages will be printed somewhere 
	 */
	public default boolean debug() {
		 return willLog(Level.DEBUG);
	}
	
	/** 
	 * @return true if info or higher messages will be printed somewhere
	 */
	public default boolean info() {
		return willLog(Level.INFO);
	}
	
	/** 
	 * @return true if warn or higher messages will be printed somewhere 
	 */
	public default boolean warn() {
		return willLog(Level.WARN);
	}
	
	/**
	 * @return true if error or higher messages will be printed somewhere
	 */
	public default boolean error() {
		return willLog(Level.ERROR);
	}
	
	/** 
	 * @return true if this logger will log a message at this provided log level,
	 * 		false otherwise.
	 * 		i.e. an INFO logger should log INFO and ERROR, but an ERROR log should only log ERRORS, not INFOs.
	 */
	public default boolean willLog(Level level) {
		return level.getValue() >= getCurrentLevel().getValue();
	}
	
	/** @return the current logging level */
	public Level getCurrentLevel();
	
	/** Set the current logging level for subsequent logging */
	public void setCurrentLevel(Level level);
	
	/** @return the current way the log prints out the system time */
	public PrintTimeOption getCurrentLogTimeOption();

}

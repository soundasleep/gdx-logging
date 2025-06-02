/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.Nullable;
import org.jevon.gdx.logging.FastLogger.PrintTimeOption;

/**
 * A logger that already has a {@link #getTag() tag} built-in.
 *  
 * <p>
 * Example code:
 * 
 * <pre>
 * if (log.info()) { // optional
 *   log.info("%s is less than %s", 1, 2); 
 * }
 * </pre>
 * 
 * @author Jevon
 *
 */
public interface GdxLog extends CommonLogMethods {

	/**
	 * Helpful method to create a new log quickly.
	 * For example,
	 * 
	 * <pre>GdxLog log = GdxLog.newLog("my-library");</pre>
	 */
	public static GdxLog newLog(String tag) {
		return new TaggedToGdxLogger(tag);
	}
	
	/**
	 * Create a new logger with the simple name of the given class. 
	 * For example,
	 * 
	 * <pre>GdxLog log = GdxLog.newLog(String.class);</pre>
	 * 
	 * will return a logger with the tag "String".
	 */
	public static GdxLog newLog(Class<?> cls) {
		String name = cls.getSimpleName();
		if (name == null) {
			name = "null";
		}
		return new TaggedToGdxLogger(name);
	}
	
	
	/** @return the parent logger */
	public FastLogger getParentLogger();
	
	/** @return the current logging level */
	@Override
	public default Level getCurrentLevel() {
		return getParentLogger().getCurrentLevel();
	}
	
	/** Set the current logging level for subsequent logging */
	@Override
	public default void setCurrentLevel(Level level) {
		getParentLogger().setCurrentLevel(level);
	}

	/** @return the current way the log prints out the system time */
	@Override
	public default PrintTimeOption getCurrentLogTimeOption() {
		return getParentLogger().getCurrentLogTimeOption();
	}
	
	/** Set the way the log prints out the system time at log time */
	public default void setCurrentLogTimeOption(PrintTimeOption option) {
		getParentLogger().setCurrentLogTimeOption(option);
	}
	
	/**
	 * Get the tag for this logger.
	 */
	public String getTag();
	
	/**
	 * If we are logging at the given level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public default void log(Level level, String message, @Nullable Object @Nullable... args) {
		getParentLogger().log(level, getTag(), message, args);
	}
	
	/**
	 * Log relatively detailed tracing used by application developers.
	 * 
	 * <p>
	 * If we are logging at the {@link #debug()} level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public default void debug(String message, @Nullable Object @Nullable... args) {
		log(Level.DEBUG, message, args);
	}
	
	/**
	 * Log informational messages that might make sense to end users and system administrators, and highlight the progress of the application.
	 * 
	 * <p>
	 * If we are logging at the {@link #info()} level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public default void info(String message, @Nullable Object... args) {
		log(Level.INFO, message, args);
	}
	
	/**
	 * Log potentially harmful situations of interest to end users or system managers that indicate potential problems.
	 * 
	 * <p>
	 * If we are logging at the {@link #warn()} level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public default void warn(String message, @Nullable Object... args) {
		log(Level.WARN, message, args);
	}
	
	/**
	 * Log error events of considerable importance that will prevent normal program execution, but might still allow the application to continue running.
	 * 
	 * <p>
	 * If we are logging at the {@link #error()} level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public default void error(String message, @Nullable Object... args) {
		log(Level.ERROR, message, args);
	}

	/**
	 * Log the given throwable, with no other context, to the logger
	 */
	public default void throwable(Throwable cause) {
		getParentLogger().throwable(getTag(), cause);
	}
	
}

/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.jevon.gdx.logging.FastLogger.Level;

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
@NonNullByDefault
public interface GdxLog {

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

	/** @return true if debug or higher messages will be printed somewhere */
	public default boolean debug() {
		 return getParentLogger().willLog(Level.DEBUG);
	}
	
	/** @return true if info or higher messages will be printed somewhere */
	public default boolean info() {
		return getParentLogger().willLog(Level.INFO);
	}
	
	/** @return true if warn or higher messages will be printed somewhere */
	public default boolean warn() {
		return getParentLogger().willLog(Level.WARN);
	}
	
	/** @return true if error or higher messages will be printed somewhere */
	public default boolean error() {
		return getParentLogger().willLog(Level.ERROR);
	}
	
	public default boolean willLog(Level level) {
		return getParentLogger().getCurrentLevel().value >= level.value;
	}
	
	/** @return the current logging level */
	public default Level getCurrentLevel() {
		return getParentLogger().getCurrentLevel();
	}
	
	/** Set the current logging level for subsequent logging */
	public default void setCurrentLevel(Level level) {
		getParentLogger().setCurrentLevel(level);
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
	public default void log(Level level, String message, @Nullable Object... args) {
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
	public default void debug(String message, @Nullable Object... args) {
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

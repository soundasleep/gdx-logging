/**
 * 
 */
package org.jevon.gdx.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A nice, simple logger interface that satisfies the following constraints:
 * 
 * <ol>
 * <li>It must <em>always</em> be safe, regardless of what messages might be passed to it. All content is not trusted. None of this DNS-resolving, JDNI, or memory corruption shenanigans.
 * <li>It must be fast and easily optimisable by a JIT. The interface should be designed to minimise the amount of computation that a client might need to do,
 * 		e.g. no {@code log("message that " + value + " forces string concatenation, even if it might not be logged")}
 * </ol>
 * 
 * <p>
 * Log levels, in order of severity:
 * 
 * <ul>
 *   <li><b>error</b> - Error events of considerable importance that will prevent normal program execution, but might still allow the application to continue running.
 *   <li><b>warn</b> - Potentially harmful situations of interest to end users or system managers that indicate potential problems.
 *   <li><b>info</b> - Informational messages that might make sense to end users and system administrators, and highlight the progress of the application.
 *   <li><b>debug</b> - Relatively detailed tracing used by application developers.
 * </li>
 * 
 * <p>
 * Example code:
 * 
 * <pre>
 * if (log.info()) { // optional
 *   log.info("my-library", "%s is less than %s", 1, 2); 
 * }
 * </pre>
 * 
 * @author Jevon
 *
 */
@NonNullByDefault
public interface FastLogger {

	public static enum Level {
		/** Don't log anything. */
		NONE(5),
		/** Error events of considerable importance that will prevent normal program execution, but might still allow the application to continue running. */		
		ERROR(4),
		/** Potentially harmful situations of interest to end users or system managers that indicate potential problems. */
		WARN(3),
		/** Informational messages that might make sense to end users and system administrators, and highlight the progress of the application. */
		INFO(2),
		/** Relatively detailed tracing used by application developers. */
		DEBUG(1),
		;

		final int value;
		
		Level(int value) {
			this.value = value;
		}
	}
	
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
	
	public default boolean willLog(Level level) {
		return getCurrentLevel().value >= level.value;
	}
	
	/** @return the current logging level */
	public Level getCurrentLevel();
	
	/** Set the current logging level for subsequent logging */
	public void setCurrentLevel(Level level);

	/**
	 * If we are logging at the given level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public void log(Level level, String tag, String message, @Nullable Object... args);
	
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
	public default void debug(String tag, String message, @Nullable Object... args) {
		log(Level.DEBUG, tag, message, args);
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
	public default void info(String tag, String message, @Nullable Object... args) {
		log(Level.INFO, tag, message, args);
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
	public default void warn(String tag, String message, @Nullable Object... args) {
		log(Level.WARN, tag, message, args);
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
	public default void error(String tag, String message, @Nullable Object... args) {
		log(Level.ERROR, tag, message, args);
	}
	
	public default void throwable(String tag, Throwable cause) {
		StringWriter sw = new StringWriter();
		cause.printStackTrace(new PrintWriter(sw));
		String s = sw.toString();
		if (s == null) {
			throw new NullPointerException("unexpected null toString");
		}
		log(Level.ERROR, tag, s);
	}
	
}

/**
 * 
 */
package org.jevon.gdx.logging;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

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
public interface FastLogger extends CommonLogMethods {

	/**
	 * Different predefined ways to print the date/time before log messages.
	 * 
	 * @author jevon
	 *
	 */
	public static enum PrintTimeOption {
		
		NONE,
		
		/** hh:mm:ss.nnn */
		ISO_LOCAL_TIME,
		;
		
		public static final DateTimeFormatter OUR_ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
	                .appendValue(HOUR_OF_DAY, 2)
	                .appendLiteral(':')
	                .appendValue(MINUTE_OF_HOUR, 2)
	                .optionalStart()
	                .appendLiteral(':')
	                .appendValue(SECOND_OF_MINUTE, 2)
	                .appendLiteral('.')
	                .appendFraction(NANO_OF_SECOND, 3, 3, false)
	                .toFormatter();
		
		/** @return a string to prefix to log messages representing the current time, can be the empty string */
		public String getCurrentPrintTime() {
			switch (this) {
			case NONE:
				return "";
			case ISO_LOCAL_TIME:
				return getIsoLocalTime();
			default:
				throw new IllegalStateException("unknown option " + this);
			}
		}
		
		public boolean isBlank() {
			return this == NONE;
		}
		
		private static String getIsoLocalTime() {
			LocalTime now = LocalTime.now();
			return now.format(OUR_ISO_LOCAL_TIME);
		}

	}
	
	/** Set the way the log prints out the system time at log time */
	public void setCurrentLogTimeOption(PrintTimeOption option);

	/**
	 * If we are logging at the given level, log the given message,
	 * optionally {@link String#format(String, Object...) formatted} with the given arguments.
	 * 
	 * @param tag grouping label
	 * @param message unformatted message with %s for formatting
	 * @param args optional parameters to pass to {@link String#format(String, Object...)}
	 */
	public void log(Level level, String tag, String message, @Nullable Object @Nullable... args);

	/**
	 * Actually do whatever logging we want to do.
	 * We've already done the level check and the message formatting (if any).
	 */
	public void actuallyLog(Level level, String printTime, String tag, String formattedMessage);

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
	public default void debug(String tag, String message, @Nullable Object @Nullable... args) {
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
		log(Level.ERROR, tag, "%s", s);
	}
	
}

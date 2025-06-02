/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Common log behaviour.
 * 
 * @author Jevon
 *
 */
public abstract class AbstractLogger implements FastLogger {
	
	/** default logging level: INFO */
	public static final Level DEFAULT_LEVEL = Level.INFO;
	
	protected Level currentLevel;
	
	/** default print time option: NONE */
	public static final PrintTimeOption DEFAULT_PRINT_TIME = PrintTimeOption.NONE;
	
	protected PrintTimeOption currentPrintTime;
	
	/** 
	 * Create a new logger with the default logging level {@link #DEFAULT_LEVEL} and {@link #DEFAULT_PRINT_TIME}.
	 */
	public AbstractLogger() {
		this(DEFAULT_LEVEL, DEFAULT_PRINT_TIME);
	}
	
	/**
	 * @param level the logging level to start at 
	 **/
	public AbstractLogger(Level level, PrintTimeOption printTime) {
		currentLevel = level;
		currentPrintTime = printTime;
	}

	@Override
	public Level getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void setCurrentLevel(Level level) {
		currentLevel = level;
	}

	@Override
	public PrintTimeOption getCurrentLogTimeOption() {
		return currentPrintTime;
	}

	@Override
	public void setCurrentLogTimeOption(PrintTimeOption option) {
		currentPrintTime = option;
	}

	@Override
	public void log(Level level, String tag, String message, @Nullable Object @Nullable... args) {
		if (level.getValue() >= getCurrentLevel().getValue()) {
			String formatted = String.format(message, args);
			String printTime = currentPrintTime.getCurrentPrintTime();
			actuallyLog(level, printTime, tag, expectNonNull(formatted));
		}
	}
	
	/** 
	 * Ensure that the given value is not {@code null}, or throws
	 * a {@link NullPointerException}.
	 */
	protected static <V> @NonNull V requireNonNull(@Nullable V value) {
		if (value == null) {
			throw new NullPointerException(String.format("%s should not be null", value));
		}
		
		return value;
	}	
	/** 
	 * Ensure that the given value is not {@code null}, or throws
	 * a {@link NullPointerException}.
	 */
	protected static <V> @NonNull V requireNonNull(String message, @Nullable V value) {
		if (value == null) {
			throw new NullPointerException(String.format("%s: %s should not be null", message, value));
		}
		
		return value;
	}
	
	/** 
	 * We would never, ever expect the given object to be null. If it is, then
	 * we have made a terrible assumption, or perhaps the JVM has changed its API somehow.
	 */
	protected static <V> @NonNull V expectNonNull(@Nullable V value) {
		if (value == null) {
			throw new AssertionError(String.format("%s should never be null", value));
		}
		
		return value;
	}
	
	/**
	 * Actually do whatever logging we want to do.
	 * We've already done the level check and the message formatting (if any).
	 */
	@Override
	public abstract void actuallyLog(Level level, String printTime, String tag, String formattedMessage);

}

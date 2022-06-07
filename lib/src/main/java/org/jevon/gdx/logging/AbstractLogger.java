/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Common log behaviour.
 * 
 * @author Jevon
 *
 */
public abstract class AbstractLogger implements FastLogger {
	
	private static final @NonNull Level DEFAULT_LEVEL = Level.WARN;
	
	protected @NonNull Level currentLevel;
	
	/** 
	 * Create a new logger with the default logging level {@link #DEFAULT_LEVEL}.
	 */
	public AbstractLogger() {
		this(DEFAULT_LEVEL);
	}
	
	/**
	 * @param level the logging level to start at 
	 **/
	public AbstractLogger(@NonNull Level level) {
		currentLevel = level;
	}

	@Override
	public @NonNull Level getCurrentLevel() {
		return currentLevel;
	}

	@Override
	public void setCurrentLevel(@NonNull Level level) {
		currentLevel = level;
	}

	@Override
	public void log(@NonNull Level level, @NonNull String tag, @NonNull String message, Object... args) {
		if (level.value >= getCurrentLevel().value) {
			String formatted = String.format(message, args);
			actuallyLog(level, tag, expectNonNull(formatted));
		}
	}
	
	/** 
	 * Ensure that the given value is not {@code null}, or throws
	 * a {@link NullPointerException}.
	 */
	protected static <V> @NonNull V requireNonNull(V value) {
		if (value == null) {
			throw new NullPointerException(String.format("%s should not be null", value));
		}
		
		return value;
	}
	
	/** 
	 * We would never, ever expect the given object to be null. If it is, then
	 * we have made a terrible assumption, or perhaps the JVM has changed its API somehow.
	 */
	protected static <V> @NonNull V expectNonNull(V value) {
		if (value == null) {
			throw new AssertionError(String.format("%s should never be null", value));
		}
		
		return value;
	}
	
	/**
	 * Actually do whatever logging we want to do.
	 * We've already done the level check and the message formatting (if any).
	 */
	protected abstract void actuallyLog(@NonNull Level level, @NonNull String tag, @NonNull String formattedMessage);

}

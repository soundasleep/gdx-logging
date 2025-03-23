/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.Nullable;

import com.badlogic.gdx.ApplicationLogger;

/**
 * Implements all of the behaviour necessary to connect a {@link ApplicationLogger} with our {@link FastLogger}.
 * 
 * @author Jevon
 *
 */
public abstract class GdxApplicationLogger extends AbstractLogger implements ApplicationLogger {
	
	/** 
	 * Create a new logger with the default logging level {@link AbstractLogger#DEFAULT_LEVEL} 
	 * and {@link AbstractLogger#DEFAULT_PRINT_TIME}.
	 */
	public GdxApplicationLogger() {
		this(DEFAULT_LEVEL, DEFAULT_PRINT_TIME);
	}

	/**
	 * @param level the logging level to start at 
	 **/
	public GdxApplicationLogger(Level level) {
		this(level, DEFAULT_PRINT_TIME);
	}
	
	public GdxApplicationLogger(Level level, PrintTimeOption option) {
		super(level, option);
	}
	
	/**
	 * Print the given stack trace somewhere.
	 */
	protected abstract void printStackTrace(Level level, Throwable exception);

	@Override
	public void log(@Nullable String tag, @Nullable String message) {
		super.info(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void log(@Nullable String tag, @Nullable String message, @Nullable Throwable exception) {
		log(tag, message);
		printStackTrace(Level.INFO, requireNonNull("exception", exception));
	}

	@Override
	public void error(@Nullable String tag, @Nullable String message) {
		super.error(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void error(@Nullable String tag, @Nullable String message, @Nullable Throwable exception) {
		error(tag, message);
		printStackTrace(Level.ERROR, requireNonNull("exception", exception));
	}

	@Override
	public void debug(@Nullable String tag, @Nullable String message) {
		super.debug(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void debug(@Nullable String tag, @Nullable String message, @Nullable Throwable exception) {
		debug(tag, message);
		printStackTrace(Level.DEBUG, requireNonNull("exception", exception));
	}

}

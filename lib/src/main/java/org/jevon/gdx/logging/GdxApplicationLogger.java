/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;

import com.badlogic.gdx.ApplicationLogger;

/**
 * Implements all of the behaviour necessary to connect a {@link ApplicationLogger} with our {@link FastLogger}.
 * 
 * @author Jevon
 *
 */
public abstract class GdxApplicationLogger extends AbstractLogger implements ApplicationLogger {
	
	/** 
	 * Create a new logger with the default logging level {@link AbstractLogger#DEFAULT_LEVEL}.
	 */
	public GdxApplicationLogger() {
		super();
	}

	/**
	 * @param level the logging level to start at 
	 **/
	public GdxApplicationLogger(@NonNull Level level) {
		super(level);
	}
	
	/**
	 * Print the given stack trace somewhere.
	 */
	protected abstract void printStackTrace(@NonNull Level level, Throwable exception);

	@Override
	public void log(String tag, String message) {
		super.info(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		log(tag, message);
		printStackTrace(Level.INFO, exception);
	}

	@Override
	public void error(String tag, String message) {
		super.error(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		error(tag, message);
		printStackTrace(Level.ERROR, exception);
	}

	@Override
	public void debug(String tag, String message) {
		super.debug(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		debug(tag, message);
		printStackTrace(Level.DEBUG, exception);
	}

}

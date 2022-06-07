/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;

import com.badlogic.gdx.ApplicationLogger;

/**
 * Copy-paste of libgdx's default {@code LwjglApplicationLogger}.
 * 
 * @author Jevon
 *
 */
public class SystemOutLogger extends AbstractLogger implements ApplicationLogger {

	@Override
	protected void actuallyLog(@NonNull Level level, @NonNull String tag, @NonNull String formattedMessage) {
		System.out.println(String.format("[%s] %s", tag, formattedMessage));
	}
	
	@Override
	public void log(String tag, String message) {
		super.info(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		log(tag, message);
		exception.printStackTrace(System.out);
	}

	@Override
	public void error(String tag, String message) {
		super.error(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		error(tag, message);
		exception.printStackTrace(System.err);
	}

	@Override
	public void debug(String tag, String message) {
		super.debug(requireNonNull(tag), requireNonNull(message));
	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		debug(tag, message);
		exception.printStackTrace(System.out);
	}

}

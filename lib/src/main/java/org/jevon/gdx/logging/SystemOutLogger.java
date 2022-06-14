/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Copy-paste of libgdx's default {@code LwjglApplicationLogger}.
 * 
 * @author Jevon
 *
 */
public class SystemOutLogger extends GdxApplicationLogger {
	
	/** 
	 * Create a new logger with the default logging level {@link AbstractLogger#DEFAULT_LEVEL}.
	 */
	public SystemOutLogger() {
		super();
	}

	/**
	 * @param level the logging level to start at 
	 **/
	public SystemOutLogger(@NonNull Level level) {
		super(level);
	}

	@Override
	protected void actuallyLog(@NonNull Level level, @NonNull String tag, @NonNull String formattedMessage) {
		System.out.println(String.format("[%s] %s", tag, formattedMessage));
	}
	
	@Override
	protected void printStackTrace(@NonNull Level level, Throwable exception) {
		if (level.value >= Level.WARN.value) {
			exception.printStackTrace(System.err);
		} else {
			exception.printStackTrace(System.out);
		}
	}

}

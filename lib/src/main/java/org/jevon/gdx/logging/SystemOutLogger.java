/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * Copy-paste of libgdx's default {@code LwjglApplicationLogger}.
 * If the log level is >= {@Link Level#ERROR}, it is printed to stderr;
 * otherwise, printed to stdout.
 * 
 * @author Jevon
 *
 */
@NonNullByDefault
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
	public SystemOutLogger(Level level) {
		super(level);
	}

	@Override
	protected void actuallyLog(Level level, String tag, String formattedMessage) {
		String toPrint = "[" + tag + "] " + formattedMessage;
		if (level.value >= Level.ERROR.value) {
			System.err.println(toPrint);
		} else {
			System.out.println(toPrint);
		}
	}
	
	@Override
	protected void printStackTrace(Level level, Throwable exception) {
		if (level.value >= Level.ERROR.value) {
			exception.printStackTrace(System.err);
		} else {
			exception.printStackTrace(System.out);
		}
	}

}

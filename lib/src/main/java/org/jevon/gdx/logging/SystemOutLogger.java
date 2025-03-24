/**
 * 
 */
package org.jevon.gdx.logging;

/**
 * Copy-paste of libgdx's default {@code LwjglApplicationLogger}.
 * If the log level is >= {@Link Level#ERROR}, it is printed to stderr;
 * otherwise, printed to stdout.
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
	public SystemOutLogger(Level level) {
		super(level);
	}
	
	/** @return the actual log message that will be printed */
	public static String getLogMessage(Level level, String printTime, String tag, String formattedMessage) {
		String toPrint = "[" + tag + "] " + formattedMessage;
		if (!printTime.isEmpty()) {
			toPrint = printTime + " " + toPrint;
		}
		return toPrint;
	}

	@Override
	public void actuallyLog(Level level, String printTime, String tag, String formattedMessage) {
		String toPrint = getLogMessage(level, printTime, tag, formattedMessage);
		
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

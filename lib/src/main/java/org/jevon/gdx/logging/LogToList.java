/**
 * 
 */
package org.jevon.gdx.logging;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * A simple logger that logs to a list of strings. Mostly used for testing.
 * <strong>NOT</strong> thread-safe or synchronized.
 * 
 * @author Jevon
 *
 */
@NonNullByDefault
public class LogToList extends GdxApplicationLogger {
	
	private final List<String> list = new ArrayList<>(); 
	
	/** 
	 * Create a new logger with the default logging level {@link #DEFAULT_LEVEL}.
	 */
	public LogToList() {
		super();
	}

	/**
	 * @param level the logging level to start at 
	 **/
	public LogToList(Level level) {
		super(level);
	}

	@Override
	protected void actuallyLog(Level level, String tag, String formattedMessage) {
		list.add(expectNonNull(String.format("[%s] %s", tag, formattedMessage)));
	}

	/**
	 * @return the list of log messages (not a copy; you should make a copy of this if you are about to iterate over it).
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * Clear the log.
	 */
	public void clear() {
		list.clear();
	}

	@Override
	protected void printStackTrace(Level level, Throwable exception) {
		String message = exception.getMessage();
		if (message == null) {
			message = "null";
		}
		
		list.add(message);
	}
	
}

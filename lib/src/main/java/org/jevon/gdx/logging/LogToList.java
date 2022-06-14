/**
 * 
 */
package org.jevon.gdx.logging;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

/**
 * A simple logger that logs to a list of strings. Mostly used for testing.
 * <strong>NOT</strong> thread-safe or synchronized.
 * 
 * @author Jevon
 *
 */
public class LogToList extends GdxApplicationLogger {
	
	private final @NonNull List<@NonNull String> list = new ArrayList<>(); 
	
	/** 
	 * Create a new logger with the default logging level {@link #DEFAULT_LEVEL}.
	 */
	public LogToList() {
		super();
	}

	/**
	 * @param level the logging level to start at 
	 **/
	public LogToList(@NonNull Level level) {
		super(level);
	}

	@Override
	protected void actuallyLog(@NonNull Level level, @NonNull String tag, @NonNull String formattedMessage) {
		list.add(expectNonNull(String.format("[%s] %s", tag, formattedMessage)));
	}

	/**
	 * @return the list of log messages (not a copy; you should make a copy of this if you are about to iterate over it).
	 */
	public List<@NonNull String> getList() {
		return list;
	}

	/**
	 * Clear the log.
	 */
	public void clear() {
		list.clear();
	}

	@Override
	protected void printStackTrace(@NonNull Level level, Throwable exception) {
		String message = exception.getMessage();
		if (message == null) {
			message = "null";
		}
		
		list.add(message);
	}
	
}

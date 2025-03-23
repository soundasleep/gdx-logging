package org.jevon.gdx.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Prints log messages to system.out, but also stores a cache
 * of these log messages so they can be restored once an actual Gdx.app is available.
 */
public class TemporarilyCachedLogger extends SystemOutLogger {
	
	public static class CachedLogMessage {
		private final Level level;
		private final String printTime;
		private final String tag;
		private final String formattedMessage;
		
		public CachedLogMessage(Level level, String printTime, String tag, String formattedMessage) {
			super();
			this.level = level;
			this.printTime = printTime;
			this.tag = tag;
			this.formattedMessage = formattedMessage;
		}

		public Level getLevel() {
			return level;
		}

		public String getPrintTime() {
			return printTime;
		}

		public String getTag() {
			return tag;
		}

		public String getFormattedMessage() {
			return formattedMessage;
		}
		
	}

	/** you must lock on {@link #lock} to access this */
	private static final List<CachedLogMessage> cachedLogs = new ArrayList<>(); 
	
	private static final Object lock = new Object();
	
	@Override
	protected void actuallyLog(Level level, String printTime, String tag, String formattedMessage) {
		// add to the log cache
		synchronized (lock) {
			cachedLogs.add(new CachedLogMessage(level, printTime, tag, formattedMessage));
		}
		
		// print to stdout
		super.actuallyLog(level, printTime, tag, formattedMessage);
	}
	
	/** @return all of the cached logs as a copied list, and then resets the cached logs */
	public List<CachedLogMessage> getAllCachedLogsAndClear() {
		synchronized (lock) {
			List<CachedLogMessage> copy = new ArrayList<>(cachedLogs);
			cachedLogs.clear();
			return copy;
		}
	}
			
}
/**
 * 
 */
package org.jevon.gdx.logging;

import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.jevon.gdx.logging.TemporarilyCachedLogger.CachedLogMessage;

import com.badlogic.gdx.Gdx;

/**
 * A logger with a tag that passes everything along to {@link Gdx#app}.
 * The logger also keeps a cache of messages logged before {@link Gdx#app} is set,
 * and once this log is available, all of these cached messages
 * are inserted.
 * 
 * @author Jevon
 *
 */
public class TaggedToGdxLogger implements GdxLog {  
	
	private final String tag;
	
	private static final TemporarilyCachedLogger DEFAULT_LOGGER = new TemporarilyCachedLogger();
	
	private static FastLogger currentFallbackLoggerIfGdxAppNotSetupYet = DEFAULT_LOGGER;
	
	/** 
	 * the logger we will actually log to;
	 * may temporarily be {@code null} while the Gdx application is booting up,
	 * but will be an instance of a logger once Gdx.app is set.
	 */
	private @Nullable FastLogger parentLogger;
	
	public TaggedToGdxLogger(String tag) {
		// if Gdx.app hasn't been set up yet, this will return null.
		// we will initialise it later when we try to log, hopefully Gdx.app
		// will be set by then
		this.parentLogger = initialiseParentLogger(false);
		this.tag = tag;
	}
	
	/**
	 * @param warnIfNull if true, and Gdx.app is null, display a warning message
	 * @return the parent logger from Gdx.app, or {@code null} if Gdx.app is null
	 */
	protected static @Nullable FastLogger initialiseParentLogger(boolean warnIfNull) {
		if (Gdx.app == null) {
			if (warnIfNull) {
				System.err.println("[gdx-logging] warning: Gdx.app is null: falling back to DEFAULT_LOGGER");
			}
			return null;
		} else {
			if (!(Gdx.app.getApplicationLogger() instanceof FastLogger)) {
				Gdx.app.setApplicationLogger(DEFAULT_LOGGER);
			} 
			
			return (FastLogger) Gdx.app.getApplicationLogger();
		}
	}
	
	@Override
	public FastLogger getParentLogger() {
		FastLogger parentLogger = this.parentLogger;
		if (parentLogger == null) {
			this.parentLogger = parentLogger = initialiseParentLogger(true);

			if (parentLogger == null) {
				// if Gdx.app is _still_ not set up, fallback temporarily
				return currentFallbackLoggerIfGdxAppNotSetupYet;
			} else {
				// we now have a log that we can print to!
				// print out any previously cached log messages
				List<CachedLogMessage> cached = DEFAULT_LOGGER.getAllCachedLogsAndClear();
				if (!cached.isEmpty()) {
					parentLogger.info("internal", "Restoring %s cached log messages from startup...", cached.size());
					for (CachedLogMessage s : cached) {
						parentLogger.log(s.getLevel(), s.getPrintTime(), s.getTag(), s.getFormattedMessage());
					}
				}
				hasRestoredCachedLogs = true;
			}
		}		
		
		return parentLogger;
	}
	
	/**
	 * By default, new logs will default to {@link #DEFAULT_LOGGER} when Gdx.app
	 * has not been initialised yet; this method allows tests and clients
	 * to override this fallback.
	 */
	public static void setFallbackLoggerIfGdxAppNotSetupYet(FastLogger logger) {
		currentFallbackLoggerIfGdxAppNotSetupYet = logger;
	}

	@Override
	public String getTag() {
		return tag;
	}

	private boolean hasRestoredCachedLogs = false;

	public boolean isHasRestoredCachedLogs() {
		return hasRestoredCachedLogs;
	}

	/** @return the default logger that all {@link TaggedToGdxLogger}s use 
	 * 		when {@link Gdx#app} is not available, e.g. during startup */
	public static TemporarilyCachedLogger getDefaultLogger() {
		return DEFAULT_LOGGER;
	}
	
}

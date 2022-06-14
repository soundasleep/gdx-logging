/**
 * 
 */
package org.jevon.gdx.logging;

import org.eclipse.jdt.annotation.NonNull;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

/**
 * A logger with a tag that passes everything along to {@link Gdx.app}.
 * 
 * @author Jevon
 *
 */
public class TaggedToGdxLogger implements GdxLog {  
	
	protected final @NonNull String tag;
	
	protected final static ApplicationLogger DEFAULT_LOGGER = new SystemOutLogger();
	
	protected FastLogger parentLogger;
	
	public TaggedToGdxLogger(@NonNull String tag) {
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
	protected static FastLogger initialiseParentLogger(boolean warnIfNull) {
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
		if (parentLogger == null) {
			parentLogger = initialiseParentLogger(true);

			if (parentLogger == null) {
				// if Gdx.app is _still_ not set up, fallback temporarily
				return (FastLogger) DEFAULT_LOGGER;
			}
		}		
		
		return parentLogger;
	}

	@Override
	public @NonNull String getTag() {
		return tag;
	}
	
}

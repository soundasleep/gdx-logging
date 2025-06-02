package org.jevon.gdx.logging.slf4j;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;
import org.jevon.gdx.logging.GdxLog;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.AbstractLogger;

/**
 * Slf4j connection to our gdx logger.
 */
public class Slf4jGdxLogger extends AbstractLogger implements ILoggerFactory {
	
	private static final long serialVersionUID = 1L;
	private final GdxLog log;
	
	public Slf4jGdxLogger(GdxLog log) {
		this.log = log;
	}
	
	public Slf4jGdxLogger() {
		this(GdxLog.newLog(Slf4jGdxLogger.class));
	}
	
	@Override
	public boolean isTraceEnabled() {
		return false; // does nothing
	}

	@Override
	public boolean isTraceEnabled(@Nullable Marker marker) {
		return false; // does nothing
	}

	@Override
	public boolean isDebugEnabled() {
		return log.debug();
	}

	@Override
	public boolean isDebugEnabled(@Nullable Marker marker) {
		return isDebugEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return log.info();
	}

	@Override
	public boolean isInfoEnabled(@Nullable Marker marker) {
		return isInfoEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return log.warn();
	}

	@Override
	public boolean isWarnEnabled(@Nullable Marker marker) {
		return isWarnEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return log.error();
	}

	@Override
	public boolean isErrorEnabled(@Nullable Marker marker) {
		return isErrorEnabled();
	}

	/** @deprecated throws UnsupportedOperationException, I have no idea what this method is used for */
	@Override
	@Deprecated
	protected String getFullyQualifiedCallerName() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleNormalizedLoggingCall(@Nullable Level level, @Nullable Marker marker, @Nullable String messagePattern, Object @Nullable [] arguments,
			@Nullable Throwable throwable) {
		if (level == null) {
			throw new NullPointerException("null level");
		}
		if (messagePattern == null) {
			messagePattern = "<null>";
		}
		
		org.jevon.gdx.logging.Level convertedLevel = switch (level) {
		case DEBUG -> org.jevon.gdx.logging.Level.DEBUG;
		case ERROR -> org.jevon.gdx.logging.Level.ERROR;
		case INFO -> org.jevon.gdx.logging.Level.INFO;
		case TRACE -> org.jevon.gdx.logging.Level.DEBUG;
		case WARN -> org.jevon.gdx.logging.Level.WARN;
		default -> throw new IllegalArgumentException("unknown level " + level);
		};
		
		log.log(convertedLevel, "%s", formatSlf4jMessage(messagePattern, arguments));
		
		if (throwable != null) {
			log.throwable(throwable);
		}
	}

	/**
	 * Converts a string like "a {} b {} c" into specific arguments.
	 * I'm sure there is a proper library to use here but I can't immediately
	 * find out what it is. 
	 * Probably something Apache-related.
	 */
	private String formatSlf4jMessage(String message, Object @Nullable [] arguments) {
		if (arguments == null) {
			arguments = new Object[] {};
		}
		
		String result = "";
		int arg = 0;
		for (int i = 0; i < message.length(); i++) {
			int index = message.indexOf("{}", i);
			if (index == -1) {
				// all done
				result += message.substring(i);
				break;
			} else {
				result += message.substring(i, index);
				result += arguments[arg];
				arg += 1;
				i = index + 1;
			}
		}
		
		return result;
	}
	
	private static final Set<String> disableInfoLoggingForClasses = new HashSet<>();
	private static final Object disableInfoLoggingForClassesLock = new Object();
	
	public static void disableInfoLoggingForName(String name) {
		synchronized (disableInfoLoggingForClassesLock) {
			disableInfoLoggingForClasses.add(name);
		}
	}

	@Override
	public Logger getLogger(@Nullable String name) {
		if (name == null) {
			name = "<null>";
		}
		
		// HACK: special case until https://github.com/radkovo/jStyleParser/issues/124 is fixed
		// or until we allow users of gdx-logging to configure slf4j logging per-name
		synchronized (disableInfoLoggingForClassesLock) {
			if (disableInfoLoggingForClasses.contains(name)) {
				return new Slf4jGdxLogger(GdxLog.newLog(name)) {
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isInfoEnabled() {
						// don't print any infos!
						return false;
					}
				};	
			}
		}
		
		return new Slf4jGdxLogger(GdxLog.newLog(name));
	}
	
}

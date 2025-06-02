package org.jevon.gdx.logging;

/**
 * Different log levels.
 */
public enum Level {
	/** Don't log anything. */
	NONE(5),
	/** Error events of considerable importance that will prevent normal program execution, but might still allow the application to continue running. */		
	ERROR(4),
	/** Potentially harmful situations of interest to end users or system managers that indicate potential problems. */
	WARN(3),
	/** Informational messages that might make sense to end users and system administrators, and highlight the progress of the application. */
	INFO(2),
	/** Relatively detailed tracing used by application developers. */
	DEBUG(1),
	;

	/** 
	 * the importance of the value. higher = more urgent.
	 */
	private final int value;
	
	Level(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
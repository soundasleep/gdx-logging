/**
 * 
 */
package org.jevon.gdx.logging;

import org.junit.Test;

/**
 * 
 * @author Jevon
 *
 */
public class SystemOutLoggerTest {

	/**
	 * Only tests that the system out logger works; doesn't test what it prints.
	 */
	@Test
	public void testIntegration() {
		SystemOutLogger log = new SystemOutLogger();
		
		log.log("tag", "log message");
		log.error("tag", "error message");
		log.info("tag", "info message");
		log.debug("tag", "debug message");
	}

}

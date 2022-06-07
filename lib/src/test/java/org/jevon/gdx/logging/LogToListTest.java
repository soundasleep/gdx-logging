/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.jevon.gdx.logging.FastLogger.Level;
import org.junit.Test;

/**
 * @author Jevon
 *
 */
public class LogToListTest {

	@Test
	public void testLogging() {
		LogToList log = new LogToList(Level.WARN);
		log.debug("tag", "debug");
		log.info("tag", "info");
		log.warn("tag", "warn");
		log.error("tag", "error");
		
		{
			List<@NonNull String> list = log.getList();		
			assertEquals(2, list.size());
			assertEquals("[tag] warn", list.get(0));
			assertEquals("[tag] error", list.get(1));
		}
		
		log.clear();

		{
			List<@NonNull String> list = log.getList();		
			assertTrue(list.isEmpty());
		}
	}

	@Test
	public void testLoggingWithParameters() {
		LogToList log = new LogToList(Level.WARN);
		log.debug("tag", "debug %s %s", 1, "two");
		log.info("tag", "info %s %s", 1, "two");
		log.warn("tag", "warn %s %s", 1, "two");
		log.error("tag", "error %s %s", 1, "two");
		
		{
			List<@NonNull String> list = log.getList();		
			assertEquals(2, list.size());
			assertEquals("[tag] warn 1 two", list.get(0));
			assertEquals("[tag] error 1 two", list.get(1));
		}
		
		log.clear();

		{
			List<@NonNull String> list = log.getList();		
			assertTrue(list.isEmpty());
		}
	}

}

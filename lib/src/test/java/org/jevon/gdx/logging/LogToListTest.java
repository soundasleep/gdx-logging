/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalTime;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.jevon.gdx.logging.FastLogger.Level;
import org.jevon.gdx.logging.FastLogger.PrintTimeOption;
import org.junit.Test;

/**
 * @author Jevon
 *
 */
@NonNullByDefault
public class LogToListTest {

	@Test
	public void testLogging() {
		LogToList log = new LogToList(Level.WARN, PrintTimeOption.NONE);
		log.debug("tag", "debug");
		log.info("tag", "info");
		log.warn("tag", "warn");
		log.error("tag", "error");
		
		{
			List<String> list = log.getList();		
			assertEquals(2, list.size());
			assertEquals("[tag] warn", list.get(0));
			assertEquals("[tag] error", list.get(1));
		}
		
		log.clear();

		{
			List<String> list = log.getList();		
			assertTrue(list.isEmpty());
		}
	}

	@Test
	public void testLoggingWithParameters() {
		LogToList log = new LogToList(Level.WARN, PrintTimeOption.NONE);
		log.debug("tag", "debug %s %s", 1, "two");
		log.info("tag", "info %s %s", 1, "two");
		log.warn("tag", "warn %s %s", 1, "two");
		log.error("tag", "error %s %s", 1, "two");
		
		{
			List<String> list = log.getList();		
			assertEquals(2, list.size());
			assertEquals("[tag] warn 1 two", list.get(0));
			assertEquals("[tag] error 1 two", list.get(1));
		}
		
		log.clear();

		{
			List<String> list = log.getList();		
			assertTrue(list.isEmpty());
		}
	}
	
	@Test
	public void testLoggingExceptions() {
		LogToList log = new LogToList(Level.WARN);
		assertTrue(log.getList().isEmpty());
		
		try {
			if (true) {
				throw new IndexOutOfBoundsException("expected");
			}
			fail("unexpected");
		} catch (IndexOutOfBoundsException e) {
			// print to log
			log.throwable("tag", e);			
		}
		
		assertEquals(1, log.getList().size());
		try {
			assertTrue(log.getList().get(0).contains("[tag] java.lang.IndexOutOfBoundsException: expected"));
		} catch (AssertionError e) {
			System.err.println("list = " + log.getList());
			throw e;
		}
	}

	@Test
	public void testPrintingTime() {
		LogToList log = new LogToList(Level.INFO);
		
		assertEquals(PrintTimeOption.ISO_LOCAL_TIME, log.getCurrentLogTimeOption());
		
		LocalTime now = LocalTime.now();
		// don't run this test if it's close to x:59, just in case the time
		// rolls over to x:00 (a bit of a hack)
		if (now.getMinute() < 59 && now.getMinute() >= 10) {
			log.info("tag", "message");
			assertEquals(1, log.getList().size());
			assertTrue(log.getList().get(0).contains(":" + now.getMinute()));
		}
	}
	
}

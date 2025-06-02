/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jevon.gdx.logging.FastLogger.PrintTimeOption;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;

/**
 * We can send log messages via slf4j without errors popping up.
 * 
 * @author Jevon
 *
 */
public class Slf4jLoggingTest {
	
	private LogToList list = new LogToList();
	
	@Before
	public void init() {
		TestFiles.initApplication();
		list.setCurrentLogTimeOption(PrintTimeOption.NONE);
		
		Gdx.app.setApplicationLogger(list);		
	}
	
	@Test
	public void test() {
		assertTrue(list.info());
		assertTrue(list.error());
		assertTrue(list.getList().isEmpty());
		Logger slf4jLogger = LoggerFactory.getLogger(Slf4jLoggingTest.class);
		slf4jLogger.info("logged via slf4j");
		assertEquals(1, list.getList().size());
		assertEquals("[org.jevon.gdx.logging.Slf4jLoggingTest] logged via slf4j", list.getList().get(0));
	}
	
	@Test
	public void testThrowable() {
		assertTrue(list.info());
		assertTrue(list.error());
		assertTrue(list.getList().isEmpty());
		Logger slf4jLogger = LoggerFactory.getLogger(Slf4jLoggingTest.class);
		try {
			throw new RuntimeException("expected via slf4j");
		} catch (RuntimeException e) {
			slf4jLogger.error("expected", e);
		}
		assertEquals(2, list.getList().size());
		assertEquals("[org.jevon.gdx.logging.Slf4jLoggingTest] expected", list.getList().get(0));
		assertTrue(list.getList().get(1).contains("expected via slf4j"));
	}

}

/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jevon
 *
 */
public class FallbackLoggerTest {
	
	private @Nullable GdxLog log;
	
	private final LogToList fallback = new LogToList();
	
	@Before
	public void init() {
		TestFiles.resetApplication();
		log = GdxLog.newLog(FallbackLoggerTest.class);
	}
	
	public static void assertEmpty(Collection<?> list) {
		if (!list.isEmpty()) {
			fail(String.format("'%s' was not empty"));
		}
	}
	
	@Test
	public void test() {
		TaggedToGdxLogger cast = (TaggedToGdxLogger) log;
		assertNotNull(cast);
		
		assertEmpty(fallback.getList());
		cast.info("test");
		assertEmpty(fallback.getList());
		TaggedToGdxLogger.setFallbackLoggerIfGdxAppNotSetupYet(fallback);
		assertEmpty(fallback.getList());
		cast.info("test2");
		assertEquals(1, fallback.getList().size());
		assertTrue(fallback.getList().get(0).contains(" test2"));
		assertFalse(cast.isHasRestoredCachedLogs());
		
		// and now if we make Gdx.app available, and try and log another message
		TestFiles.initApplication();
		cast.info("test3");
		
		// we get all of the logs
		assertTrue(cast.isHasRestoredCachedLogs());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testThrowable() {
		try {
			if (true) {
				throw new IndexOutOfBoundsException("expected");
			}
			fail("unexpected");
		} catch (IndexOutOfBoundsException e) {
			// print to log
			GdxLog log = this.log;
			assertNotNull(log);
			log.throwable(e);			
		}		
	}

}

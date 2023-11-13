/**
 * 
 */
package org.jevon.gdx.logging;

import java.util.Collection;
import static org.junit.Assert.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.Test;

/**
 * @author Jevon
 *
 */
@NonNullByDefault
public class FallbackLoggerTest {
	
	private static final GdxLog log = GdxLog.newLog(FallbackLoggerTest.class);
	
	private final LogToList fallback = new LogToList();
	
	public static void assertEmpty(Collection<?> list) {
		if (!list.isEmpty()) {
			fail(String.format("'%s' was not empty"));
		}
	}
	
	@Test
	public void test() {
		assertEmpty(fallback.getList());
		log.info("test");
		assertEmpty(fallback.getList());
		TaggedToGdxLogger.setFallbackLoggerIfGdxAppNotSetupYet(fallback);
		assertEmpty(fallback.getList());
		log.info("test2");
		assertEquals(1, fallback.getList().size());
		assertTrue(fallback.getList().get(0).contains(" test2"));
	}
	
	@Test
	public void testThrowable() {
		try {
			if (true) {
				throw new IndexOutOfBoundsException("expected");
			}
			fail("unexpected");
		} catch (IndexOutOfBoundsException e) {
			// print to log
			log.throwable(e);			
		}		
	}

}

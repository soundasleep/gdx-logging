/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.Gdx;

/**
 * @author Jevon
 *
 */
public class GdxLogTest {

	@Test
	public void test() {
		TestFiles.initApplication();
		LogToList list = new LogToList();
		Gdx.app.setApplicationLogger(list);
		GdxLog log = GdxLog.newLog("test");
		log.warn("warning");
		
		assertEquals(1, list.getList().size());
		assertEquals("[test] warning", list.getList().get(0));
	}
	
	@Test
	public void testCanThrowableAThrowableThatContainsFormatting() {
		TestFiles.initApplication();
		LogToList list = new LogToList();
		Gdx.app.setApplicationLogger(list);
		GdxLog log = GdxLog.newLog("test");
		log.throwable(new RuntimeException("%s"));
		
		assertEquals(1, list.getList().size());
		assertTrue(list.getList().get(0).startsWith("[test] java.lang.RuntimeException: %s"));
	}

}

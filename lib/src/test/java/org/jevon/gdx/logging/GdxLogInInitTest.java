/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertEquals;

import org.jevon.gdx.logging.FastLogger.Level;
import org.jevon.gdx.logging.FastLogger.PrintTimeOption;
import org.junit.Test;

import com.badlogic.gdx.Gdx;

/**
 * @author Jevon
 *
 */
public class GdxLogInInitTest {

	{
		TestFiles.initApplication();
		LogToList list = new LogToList(Level.WARN, PrintTimeOption.NONE);
		Gdx.app.setApplicationLogger(list);
	}
	
	protected static GdxLog log = GdxLog.newLog(GdxLogInInitTest.class);
	
	@Test
	public void test() {
		log.warn("warning");
		LogToList list = (LogToList) Gdx.app.getApplicationLogger();
		
		assertEquals(1, list.getList().size());
		assertEquals("[GdxLogInInitTest] warning", list.getList().get(0));
	}

}

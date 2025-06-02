/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.annotation.Nullable;
import org.jevon.gdx.logging.FastLogger.PrintTimeOption;
import org.junit.Test;

import com.badlogic.gdx.Gdx;

/**
 * @author Jevon
 *
 */
public class GdxLogInInitTest {

	protected static @Nullable GdxLog log;

	{
		// to test, we need to make sure there is no Gdx.app set
		// at the time the static #log field is created
		TestFiles.initApplication();
		LogToList list = new LogToList(Level.WARN, PrintTimeOption.NONE);
		Gdx.app.setApplicationLogger(list);
		log = GdxLog.newLog(GdxLogInInitTest.class);
	}
	
	@Test
	public void test() {
		GdxLog log = GdxLogInInitTest.log;
		assertNotNull(log);
		log.warn("warning");
		LogToList list = (LogToList) Gdx.app.getApplicationLogger();
		
		assertEquals(1, list.getList().size());
		assertEquals("[GdxLogInInitTest] warning", list.getList().get(0));
	}

}

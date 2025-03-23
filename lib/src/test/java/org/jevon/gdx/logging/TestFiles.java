/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.jdt.annotation.Nullable;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * 
 * @author Jevon
 *
 */
public class TestFiles {

	public static class BlankApplication extends ApplicationAdapter {
		
	}

	private static @Nullable HeadlessApplication headlessApplication;
	
	/**
	 * Initialise a test headless application.
	 */
	public static void initApplication() {
		if (headlessApplication == null) {
			HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
			headlessApplication = new HeadlessApplication(new BlankApplication(), config);
			assertNotNull(Gdx.files);
			assertNotNull(Gdx.app);
		}
	}
	
	public static void resetApplication() {
		headlessApplication = null;
		Gdx.files = null;
		Gdx.app = null;
		assertNull(Gdx.files);
		assertNull(Gdx.app);
	}
	
}

/**
 * 
 */
package org.jevon.gdx.logging;

import static org.junit.Assert.assertNotNull;

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

	private static HeadlessApplication headlessApplication;
	
	/**
	 * Initialise a test headless application.
	 */
	public static void initApplication() {
		if (headlessApplication == null) {
			HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
			headlessApplication = new HeadlessApplication(new BlankApplication(), config);
			assertNotNull(Gdx.files);
		}
	}
	
}

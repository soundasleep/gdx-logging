package org.jevon.gdx.logging.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.NOPMDCAdapter;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * 
 */
public class GdxLoggerServiceProvider implements SLF4JServiceProvider {
	
	@Override
	public ILoggerFactory getLoggerFactory() {
		return new Slf4jGdxLogger();
	}

	@Override
	public IMarkerFactory getMarkerFactory() {
		return new BasicMarkerFactory();
	}

	@Override
	public MDCAdapter getMDCAdapter() {
		return new NOPMDCAdapter();
	}

	@Override
	public String getRequestedApiVersion() {
		return "2.0.17";
	}

	@Override
	public void initialize() {
		// empty
	}

}

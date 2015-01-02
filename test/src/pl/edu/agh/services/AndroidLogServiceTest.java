package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.interfaces.ILogService;

/**
 * Created by Magda on 2015-01-02.
 */
public class AndroidLogServiceTest extends AndroidTestCase {

	private ILogService logService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		logService = new AndroidLogService();
	}

	public void testLogService() throws Exception {
		logService.info("Info test");
		logService.info("INFO TAG", "Info test");

		logService.debug("Debug test");
		logService.debug("DEBUG TAG", "Debug test");

		logService.error("Error test");
		logService.error("ERROR TAG", "Error test");
		logService.error("ERROR TAG", "Error test", new Exception());
		logService.error("Error test", new Exception());
	}
}

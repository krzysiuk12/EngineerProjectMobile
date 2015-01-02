package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
import pl.edu.agh.services.implementation.BaseService;
import pl.edu.agh.services.interfaces.IBaseService;

/**
 * Created by Magda on 2015-01-02.
 */
public class BaseServiceTest extends AndroidTestCase {
	IBaseService baseService;
	TestDatabaseHelper helper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		baseService = new BaseService();
	}

	public void testServerResponseOk() throws Exception {
		ResponseSerializer response = new ResponseSerializer();
		response.setStatus(ResponseStatus.OK);

		boolean result = baseService.validateServerResponse(response);
		assertTrue(result);
	}

	public void testServerResponseWithError() throws Exception {
		ResponseSerializer response = new ResponseSerializer();

		response.setStatus(ResponseStatus.ACCESS_DENIED);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.ACCESS_DENIED));

		response.setStatus(ResponseStatus.REQUEST_DENIED);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.REQUEST_DENIED));

		response.setStatus(ResponseStatus.ZERO_RESULTS);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.ZERO_RESULTS));

		response.setStatus(ResponseStatus.SERVER_SIDE_ERROR);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_SIDE_ERROR));

		response.setStatus(ResponseStatus.VALIDATION_ERROR);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.UNKNOWN_ERROR));

		response.setStatus(ResponseStatus.UNKNOWN_ERROR);
		expectSynchronizationError(response, new SynchronizationException(SynchronizationException.PredefinedExceptions.UNKNOWN_ERROR));

	}

	public void expectSynchronizationError(ResponseSerializer response, SynchronizationException exception) {
		try {
			boolean result = baseService.validateServerResponse(response);
			assertFalse(result);
			fail("LocationException should be thrown");
		} catch (SynchronizationException e) {
			assertEquals(e.getExceptionDefinition().getStringResourceId(), exception.getExceptionDefinition().getStringResourceId());
		}
	}
}

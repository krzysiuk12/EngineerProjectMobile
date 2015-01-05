package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.services.implementation.GoogleGeocodeService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IGoogleGeocodeService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Magda on 2015-01-05.
 */
public class GoogleGeocodingServiceTest extends AndroidTestCase {

	TestDatabaseHelper helper;

	IGoogleGeocodeService googleGeocodeService;

	IUserAccountManagementService userAccountManagementService;

	private CountDownLatch signal;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		userAccountManagementService = new UserAccountManagementService(helper);
		googleGeocodeService = new GoogleGeocodeService();
		signal = new CountDownLatch(1);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		helper.close();
		getContext().deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
	}

	public void testGetLocationByAddress() throws Exception {
		final UserAccount userAccount = BaseTestObject.createUserAccount(BaseTestObject.TEST_USER, BaseTestObject.TEST_USER);
		userAccountManagementService.saveUserAccount(userAccount);

		final String locationInfo = "Krakow, Wawel";

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					userAccountManagementService.logIn(userAccount.getLogin(), userAccount.getPassword());
					Location location = googleGeocodeService.getLocationByAddress(UserAccountManagementService.getToken(), locationInfo, null, null);
					assertNotNull(location);
				} catch (SynchronizationException e) {
					e.printStackTrace();
					fail("Synchronization exception: " + e.toString());
				} catch (GoogleGeocodingException e) {
					e.printStackTrace();
					fail("GoogleGeocodingException: " + e.toString());
				}
				signal.countDown();
			}
		}).start();

		awaitResponse();
	}

	public void testGetCoordinatesByAddress() throws Exception {
		final UserAccount userAccount = BaseTestObject.createUserAccount(BaseTestObject.TEST_USER, BaseTestObject.TEST_USER);
		userAccountManagementService.saveUserAccount(userAccount);

		final String locationInfo = "Krakow, Wawel";

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					userAccountManagementService.logIn(userAccount.getLogin(), userAccount.getPassword());
					Location location = googleGeocodeService.getCoordinatesByAddress(UserAccountManagementService.getToken(), locationInfo, null, null);
					assertNotNull(location);
					assertNotNull(location.getLatitude());
					assertNotNull(location.getLongitude());
				} catch (SynchronizationException e) {
					e.printStackTrace();
					fail("Synchronization exception: " + e.toString());
				} catch (GoogleGeocodingException e) {
					e.printStackTrace();
					fail("GoogleGeocodingException: " + e.toString());
				}
				signal.countDown();
			}
		}).start();

		awaitResponse();
	}

	private void awaitResponse() throws Exception {
		try {
			signal.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			fail("Timeout");
			e.printStackTrace();
		}
	}
}

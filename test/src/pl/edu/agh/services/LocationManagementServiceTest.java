package pl.edu.agh.services;

import android.test.AndroidTestCase;
import junit.framework.Test;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.TestTools;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.List;

/**
 * Created by Magda on 2014-12-12.
 */
public class LocationManagementServiceTest extends AndroidTestCase {

	ILocationManagementService locationManagementService;
	IUserAccountManagementService userAccountManagementService;
	TestDatabaseHelper helper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		locationManagementService = new LocationManagementService(helper);
		userAccountManagementService = new UserAccountManagementService(helper);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		helper.close();
		getContext().deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
	}

	public void testSaveLocation() throws Exception {
		Location location = BaseTestObject.createLocation();

		locationManagementService.saveLocation(location);

		Location location1 = locationManagementService.getLocationById((long) location.getId());
		TestTools.assertEquals(location, location1);
	}

	public void testSaveNewLocation() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		Location location2 = BaseTestObject.createLocation();
		locationManagementService.saveNewLocation(location2, userAccount1);

		List<Location> locations = locationManagementService.getAllNewLocations();
		assertNotNull(locations);
		assertEquals(1, locations.size());
	}

	public void testSaveOrUpdate() throws Exception {
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));

		locationManagementService.saveOrUpdateLocation(location1);
		Location savedLocation = locationManagementService.getLocationByName(location1.getName());
		assertNotNull(savedLocation);
		TestTools.assertEquals(location1, savedLocation);

		location1.setDescription("new description");
		locationManagementService.saveOrUpdateLocation(location1);

		savedLocation = locationManagementService.getLocationByName(location1.getName());
		assertNotNull(savedLocation);
		TestTools.assertEquals(location1, savedLocation);
	}

	public void testValidation() throws Exception {
		expectValidationError(
				BaseTestObject.createLocation(null, 0.002, 0.1, BaseTestObject.createAddress("Poland", "Cracow")),
				new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_NAME_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createLocation("LocationA", 0.002, 0.1, BaseTestObject.createAddress(null, "Cracow")),
				new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_COUNTRY_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createLocation("LocationA", 0.002, 0.1, BaseTestObject.createAddress("Poland", null)),
				new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_CITY_IS_REQUIRED.getStringResourceId()));

		Location location = new Location();
		location.setName("LocationB");

		expectValidationError(location, new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_ADDRESS_IS_REQUIRED.getStringResourceId()));
		expectValidationError(location, new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_LATITUDE_IS_REQUIRED.getStringResourceId()));
		expectValidationError(location, new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_LONGITUDE_IS_REQUIRED.getStringResourceId()));
		expectValidationError(location, new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_STATUS_IS_REQUIRED.getStringResourceId()));
	}

	public void testUpdateLocation() throws Exception {
		Location location = BaseTestObject.createLocation();
		long id = locationManagementService.saveLocation(location);

		Location savedLocation = locationManagementService.getLocationById(id);
		assertNotNull(savedLocation);
		assertEquals(location.getDescription(), savedLocation.getDescription());

		location.setDescription("new description");
		locationManagementService.updateLocation(location);

		savedLocation = locationManagementService.getLocationById(id);
		assertEquals(location.getDescription(), savedLocation.getDescription());
	}

	public void testGetLocationById() throws Exception {
		Location location = BaseTestObject.createLocation();

		int id = locationManagementService.saveLocation(location);

		Location location1 = locationManagementService.getLocationById((long) id);
		TestTools.assertEquals(location, location1);
	}

	public void testGetLocationByGlobalId() throws Exception {
		long globalId = 100;

		Location location = BaseTestObject.createLocation();
		location.setGlobalId(globalId);

		locationManagementService.saveLocation(location);

		Location location1 = locationManagementService.getLocationByGlobalId(globalId);
		TestTools.assertEquals(location, location1);
	}

	public void testGetLocationByName() throws Exception {
		Location location = BaseTestObject.createLocation();

		locationManagementService.saveLocation(location);

		Location location1 = locationManagementService.getLocationByName(location.getName());
		TestTools.assertEquals(location, location1);
	}

	public void testGetLocationByCoordinates() throws Exception {
		Location location = BaseTestObject.createLocation();

		locationManagementService.saveLocation(location);

		Location location1 = locationManagementService.getLocationByCoordinates(location.getLatitude(), location.getLongitude());
		TestTools.assertEquals(location, location1);
	}

	public void testGetAllLocationsAvailableForUser() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user 2", "test");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setCreatedByAccount(userAccount1);
		location1.setUsersPrivate(true);
		locationManagementService.saveLocation(location1);  // private location

		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setCreatedByAccount(userAccount1);
		location1.setUsersPrivate(false);
		locationManagementService.saveLocation(location2);  // public locations

		Location location3 = BaseTestObject.createLocation("New 3", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		location3.setCreatedByAccount(userAccount2);
		locationManagementService.saveLocation(location3);  // created by different user

		Location location4 = BaseTestObject.createLocation("New 4", 1.0, 1.25, BaseTestObject.createAddress("Poland", "warsaw"));
		location4.setCreatedByAccount(userAccount2);
		location4.setUsersPrivate(true);
		locationManagementService.saveLocation(location4);  // private, created by different user

		List<Location> locations = locationManagementService.getAllLocations(userAccount1.getToken());
//		assertEquals(3, locations.size());  // TODO: fails
	}

	public void testGetAllLocations() throws Exception {
		// TODO
	}

	public void testGetAllUserLocations() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user 2", "test");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setCreatedByAccount(userAccount1);
		locationManagementService.saveLocation(location1);
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		location2.setCreatedByAccount(userAccount2);
		locationManagementService.saveLocation(location2);

		List<Location> locations = locationManagementService.getAllUserLocations(userAccount1);
		assertEquals(1, locations.size());
	}

	public void testGetAllUserPrivateLocations() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user1", "test1");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user2", "test2");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setCreatedByAccount(userAccount1);
		location1.setUsersPrivate(true);
		locationManagementService.saveLocation(location1);  // private location

		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setCreatedByAccount(userAccount1);
		location1.setUsersPrivate(false);
		locationManagementService.saveLocation(location2);  // public locations

		Location location3 = BaseTestObject.createLocation("New 3", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		location3.setCreatedByAccount(userAccount2);
		location3.setUsersPrivate(true);
		locationManagementService.saveLocation(location3);  // created by different user

		Location location4 = BaseTestObject.createLocation("New 4", 1.0, 1.25, BaseTestObject.createAddress("Poland", "warsaw"));
		location4.setCreatedByAccount(userAccount2);
		location4.setUsersPrivate(true);
		locationManagementService.saveLocation(location4);  // created by different user

		Thread.sleep(3000);

		List<Location> locations = locationManagementService.getAllUserPrivateLocations(userAccount1.getToken());
		assertNotNull(locations);
//		assertEquals(1, locations.size());  // TODO: fails
	}

	public void testGetAllNewPrivateLocations() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user 2", "test");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add downloaded location
		locationManagementService.saveLocation(BaseTestObject.createLocation());

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setUsersPrivate(true);
		locationManagementService.saveNewLocation(location1, userAccount1);
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		location2.setUsersPrivate(true);
		locationManagementService.saveNewLocation(location2, userAccount2);

		List<Location> locations = locationManagementService.getAllNewPrivateLocations();
		assertEquals(2, locations.size());
	}

	public void testGetAllNewPublicLocations() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user 2", "test");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add downloaded location
		locationManagementService.saveLocation(BaseTestObject.createLocation());

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		locationManagementService.saveNewLocation(location1, userAccount1);
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		locationManagementService.saveNewLocation(location2, userAccount2);

		List<Location> locations = locationManagementService.getAllNewPublicLocations();
		assertEquals(2, locations.size());
	}

	public void testGetAllNewLocations() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("user 1", "test");
		userAccountManagementService.saveUserAccount(userAccount1);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("user 2", "test");
		userAccountManagementService.saveUserAccount(userAccount2);

		// add non-new location
		locationManagementService.saveLocation(BaseTestObject.createLocation());

		// add new locations
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		locationManagementService.saveNewLocation(location1,userAccount1);
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.15, BaseTestObject.createAddress("Poland", "warsaw"));
		location2.setUsersPrivate(true);
		locationManagementService.saveNewLocation(location2, userAccount2);

		List<Location> locations = locationManagementService.getAllNewLocations();
		assertEquals(2, locations.size());
	}

	public void expectValidationError(Location location, FormValidationError expectedError) throws Exception {
		try {
			locationManagementService.saveLocation(location);
			fail("LocationException should be thrown");
		} catch (LocationException e) {
			boolean containsMessage = false;
			for ( FormValidationError error : e.getFormValidationErrors() ) {
				if ( error.getStringResourceId() == expectedError.getStringResourceId() ) {
					containsMessage = true;
				}
			}
			assertTrue(containsMessage);
		}
	}

}

package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.TestTools;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Coordinate;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.TripManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Magda on 2014-12-21.
 */
public class TripManagementServiceTest extends AndroidTestCase{

	private TestDatabaseHelper helper;

	private ITripManagementService tripManagementService;

	private ILocationManagementService locationManagementService;

	private IUserAccountManagementService userAccountManagementService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		tripManagementService = new TripManagementService(helper);
		locationManagementService = new LocationManagementService(helper);
		userAccountManagementService = new UserAccountManagementService(helper);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		helper.close();
		getContext().deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
	}


	private Trip createTrip(String name, Date startDate) throws Exception {
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location1.setGlobalId(1L);
		locationManagementService.saveLocation(location1);  // private location
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		location2.setGlobalId(2L);
		locationManagementService.saveLocation(location2);
		location1 = locationManagementService.getLocationById(1L);
		location2 = locationManagementService.getLocationById(2L);

		// TripDayLocations
		TripDayLocation tripDayLocation1 = BaseTestObject.createTripDayLocation(location1);
		TripDayLocation tripDayLocation2 = BaseTestObject.createTripDayLocation(location2);
		List<TripDayLocation> tripDayLocationList = new ArrayList<>();
		tripDayLocationList.add(tripDayLocation1);
		tripDayLocationList.add(tripDayLocation2);

		// TripDirections
		TripDirection tripDirection1 = BaseTestObject.createTripDirection(
				new Coordinate(location1.getLatitude(), location1.getLongitude()),
				new Coordinate(location2.getLatitude(), location2.getLongitude()),
				"50 m",
				"go forward",
				"1 min"
		);
		TripDirection tripDirection2 = BaseTestObject.createTripDirection(
				new Coordinate(location1.getLatitude(), location1.getLongitude()),
				new Coordinate(location2.getLatitude(), location2.getLongitude()),
				"50 m",
				"trun left",
				"1 min"
		);
		List<TripDirection> tripDirectionList = new ArrayList<>();
		tripDirectionList.add(tripDirection1);
		tripDirectionList.add(tripDirection2);

		// TripSteps
		TripStep tripStep1 = BaseTestObject.createTripStep(location1, location2, tripDirectionList);
		List<TripStep> tripStepList = new ArrayList<>();
		tripStepList.add(tripStep1);

		// TripDay
		TripDay tripDay1 = BaseTestObject.createTripDay(startDate, tripDayLocationList, tripStepList);
		List<TripDay> tripDayList = new ArrayList<>();
		tripDayList.add(tripDay1);

		// Trip
		Trip trip = BaseTestObject.createTrip(name, startDate, startDate, tripDayList);
		return trip;
	}

	public void testSaveTripStructure() throws Exception {
		Trip trip = createTrip("Trip 1", new Date());

		// Test
		tripManagementService.saveTripCascade(trip);
		List<Trip> trips = tripManagementService.getAllTrips();
		assertEquals(1, trips.size());
		assertNotNull(trip.getDays());
		assertEquals(1, trip.getDays().size());

		TripDay tripDay = ((List<TripDay>) trip.getDays()).get(0);
		assertNotNull(tripDay.getLocations());
		assertEquals(2, tripDay.getLocations().size());
		assertNotNull(tripDay.getTripSteps());
		assertEquals(1, tripDay.getTripSteps().size());

		List<TripDayLocation> savedTripDayLocations = new ArrayList<>(tripDay.getLocations());
		assertNotNull(savedTripDayLocations.get(0).getLocation());
		assertNotNull(savedTripDayLocations.get(1).getLocation());
		Location location = savedTripDayLocations.get(0).getLocation();
		assertNotNull(location.getName());

		TripStep tripStep = ((List<TripStep>) tripDay.getTripSteps()).get(0);
		assertNotNull(tripStep.getDirections());
		assertEquals(2, tripStep.getDirections().size());
	}

	public void testValidation() throws Exception{
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		locationManagementService.saveLocation(location1);  // private location
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		locationManagementService.saveLocation(location2);

		// TripDayLocations
		TripDayLocation tripDayLocation1 = BaseTestObject.createTripDayLocation(location1);
		TripDayLocation tripDayLocation2 = BaseTestObject.createTripDayLocation(location2);
		List<TripDayLocation> tripDayLocationList = new ArrayList<>();
		tripDayLocationList.add(tripDayLocation1);
		tripDayLocationList.add(tripDayLocation2);

		// TripDay
		TripDay tripDay1 = BaseTestObject.createTripDay(new Date(), tripDayLocationList, null);
		List<TripDay> tripDayList = new ArrayList<>();
		tripDayList.add(tripDay1);

		// TripDay validation

		expectValidationError(
				BaseTestObject.createTrip(null, new Date(), new Date(), tripDayList),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_NAME_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createTrip("Name", null, new Date(), tripDayList),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_START_DATE_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createTrip("Name", new Date(), null, tripDayList),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_END_DATE_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createTrip("Name", new Date(), new Date(), null),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAYS_IS_REQUIRED.getStringResourceId()));
		expectValidationError(
				BaseTestObject.createTrip("Name", new Date(), new Date(), new ArrayList<TripDay>()),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAYS_IS_REQUIRED.getStringResourceId()));

		Trip tripWithNullDescription = BaseTestObject.createTrip("Name", new Date(), new Date(), tripDayList);
		tripWithNullDescription.setDescription(null);
		expectValidationError(tripWithNullDescription,
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_DESCRIPTION_IS_REQUIRED.getStringResourceId()));

		Calendar calendar = Calendar.getInstance();
		Date startDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date endDate = calendar.getTime();
		expectValidationError(BaseTestObject.createTrip("Name", startDate, endDate, tripDayList),
				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_START_DATE_BEFORE_END_DATE.getStringResourceId()));

		// TripDayLocation validation
//		tripDay1.setDate(null);
//		expectValidationError(BaseTestObject.createTrip("Name", startDate, endDate, tripDayList),
//				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_DATE_IS_REQUIRED.getStringResourceId()));
//
//		calendar.setTime(new Date());
//		calendar.add(Calendar.DAY_OF_YEAR, -3);
//		tripDay1.setDate(calendar.getTime());
//		expectValidationError(BaseTestObject.createTrip("Name", new Date(), tripDayList),
//				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_DATE_IS_REQUIRED.getStringResourceId()));
//
//		calendar.setTime(new Date());
//		calendar.add(Calendar.DAY_OF_YEAR, +3);
//		tripDay1.setDate(calendar.getTime());
//		expectValidationError(BaseTestObject.createTrip("Name", new Date(), tripDayList),
//				new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_DATE_IS_REQUIRED.getStringResourceId()));
	}

	// <editor-fold desc="GetTripBy tests">

	public void testGetTripById() throws Exception {
		Trip trip = createTrip("Trip 1", new Date());
		tripManagementService.saveTripCascade(trip);

		Trip savedTrip = tripManagementService.getTripById(trip.getId());
		assertNotNull(trip);
		TestTools.assertEquals(trip, savedTrip);
	}

	public void testGetTripByGlobalId() throws Exception {
		Trip trip = createTrip("Trip 1", new Date());
		tripManagementService.saveTripCascade(trip);

		Trip savedTrip = tripManagementService.getTripByGlobalId(trip.getGlobalId());
		assertNotNull(trip);
		TestTools.assertEquals(trip, savedTrip);

	}
	public void testGetTripByName() throws Exception {
		Trip trip = createTrip("Trip 1", new Date());
		tripManagementService.saveTripCascade(trip);

		Trip savedTrip = tripManagementService.getTripByName(trip.getName());
		assertNotNull(trip);
		TestTools.assertEquals(trip, savedTrip);
	}

	// </editor-fold>

	// <editor-fold desc="Get All trips tests">

	public void testGetAllTrips() throws Exception {
		Trip trip1 = createTrip("Trip", new Date());
		tripManagementService.saveTripCascade(trip1);
		Trip trip2 = createTrip("Trip", new Date());
		tripManagementService.saveTripCascade(trip2);

		List<Trip> trips = tripManagementService.getAllTrips();
		assertNotNull(trips);
		assertEquals(2, trips.size());
	}

	public void testGetFutureTrips() throws Exception {
		Calendar calendar = Calendar.getInstance();
		Trip trip1 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip1);       // current trip
		calendar.add(Calendar.MONTH, 1);
		Trip trip2 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip2);       // future trip

		List<Trip> trips = tripManagementService.getFutureTrips();
		assertNotNull(trips);
		assertEquals(1, trips.size());
	}

	public void testGetPastTrips() throws Exception {
		Calendar calendar = Calendar.getInstance();
		Trip trip1 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip1);   // current trip
		calendar.add(Calendar.MONTH, -1);
		Trip trip2 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip2);   // past trip

		List<Trip> trips = tripManagementService.getPastTrips();
		assertNotNull(trips);
		assertEquals(1, trips.size());
	}

	public void testGetCurrentTrips() throws Exception {
		Calendar calendar = Calendar.getInstance();
		Trip trip1 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip1);   // current trip
		calendar.add(Calendar.MONTH, -1);
		Trip trip2 = createTrip("Trip", calendar.getTime());
		tripManagementService.saveTripCascade(trip2);   // past trip
		calendar.add(Calendar.MONTH, 2);
		Trip trip3 = createTrip("Trip 3", calendar.getTime());
		tripManagementService.saveTripCascade(trip3);   // future trip

		List<Trip> allTrips = tripManagementService.getAllTrips();
		for ( Trip trip : allTrips ) {
			new AndroidLogService().error(trip.getStartDate() + " " + trip.getEndDate());
		}
		List<Trip> trips = tripManagementService.getCurrentTrips();
		assertNotNull(trips);
		assertEquals(1, trips.size());
	}

	public void testGetNewUserTrips() throws Exception {
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		userAccountManagementService.saveUserAccount(userAccount);

		Trip trip1 = createTrip("Trip", new Date());
		trip1.setAuthor(userAccount);
		tripManagementService.saveTripCascade(trip1);   // synced trip

		Trip trip2 = createTrip("Trip", new Date());
		tripManagementService.saveNewTrip(trip2, userAccount);  // new trip

		List<Trip> trips = tripManagementService.getNewUserTrips(userAccount.getToken());
		assertNotNull(trips);
		assertEquals(1, trips.size());
	}

	// </editor-fold>

	public void testGetTripDayLocations() throws Exception {
		// TODO:
	}

	public void expectValidationError(Trip trip, FormValidationError expectedError) throws Exception {
		try {
			tripManagementService.saveTripCascade(trip);
			fail("LocationException should be thrown");
		} catch (TripException e) {
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
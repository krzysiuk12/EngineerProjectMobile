package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Coordinate;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.SynchronizationService;
import pl.edu.agh.services.implementation.TripManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ISynchronizationService;
import pl.edu.agh.services.interfaces.ITripManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Magda on 2014-12-27.
 */
public class SynchronizationServiceTest extends AndroidTestCase {

	private TestDatabaseHelper helper;

	private ITripManagementService tripManagementService;

	private ILocationManagementService locationManagementService;

	private IUserAccountManagementService userAccountManagementService;

	private ISynchronizationService synchronizationService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		tripManagementService = new TripManagementService(helper);
		locationManagementService = new LocationManagementService(helper);
		userAccountManagementService = new UserAccountManagementService(helper);
		synchronizationService = new SynchronizationService(helper);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		helper.close();
		getContext().deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
	}

	public void testSendTrip() throws Exception {
		// Prepare data
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		userAccountManagementService.saveUserAccount(userAccount);
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
		TripDay tripDay1 = BaseTestObject.createTripDay(new Date(), tripDayLocationList, tripStepList);
		List<TripDay> tripDayList = new ArrayList<>();
		tripDayList.add(tripDay1);

		// Trip
		Trip trip = BaseTestObject.createTrip("Trip", new Date(), new Date(), tripDayList);
		trip.setAuthor(userAccount);

		tripManagementService.saveTripCascade(trip);

		// Test
		synchronizationService.sendTrips(userAccount.getToken());

		Trip savedTrip = tripManagementService.getTripById(trip.getId());
		Collection<TripDay> savedTripDays = savedTrip.getDays();
		assertNotNull(savedTripDays);
		for ( TripDay tripDay : tripManagementService.getTripDays(savedTrip) ) {
			assertNotNull(tripDay.getTripSteps());
			for ( TripStep tripStep : tripManagementService.getTripSteps(tripDay) ) {
				assertNotNull(tripStep.getDirections());
				Collection<TripDirection> savedTripDirections = tripManagementService.getTripDirections(tripStep);
				assertNotNull(savedTripDirections);
				assertTrue(savedTripDirections.size() > 0);
			}
		}
	}

}

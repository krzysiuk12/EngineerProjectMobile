package pl.edu.agh;

import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Coordinate;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Magda on 2014-12-13.
 */
public class BaseTestObject {

	public static Location createLocation(String name, double latitude, double longitude, Address address) {
		Location location = new Location();
		location.setName(name);
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setStatus(Location.Status.AVAILABLE);
		location.setUsersPrivate(false);
		location.setAddress(address);
		return location;
	}

	public static Address createAddress(String country, String city) {
		Address address = new Address();
		address.setCountry(country);
		address.setCity(city);
		return address;
	}

	public static UserAccount createUserAccount(String login, String password) {
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(login);
		userAccount.setPassword(password);
		userAccount.setToken(login);
		return userAccount;
	}

	public static Trip createTrip(String name, Date startDate, Date endDate, List<TripDay> tripDays) {
		Trip trip = new Trip();
		trip.setName(name);
		trip.setDescription("");
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		trip.setDays(tripDays);
		return trip;
	}

	public static Trip createTrip(String name, Date startDate, List<TripDay> tripDays) {
		Trip trip = new Trip();
		trip.setName(name);
		trip.setStartDate(startDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		Date endDate = calendar.getTime();
		trip.setEndDate(endDate);
		trip.setDays(tripDays);
		return trip;
	}

	public static TripDay createTripDay(Date date, List<TripDayLocation> locations, List<TripStep> steps) {
		TripDay day = new TripDay();
		day.setDate(date);
		day.setLocations(locations);
		day.setTripSteps(steps);
		return day;
	}

	public static TripDayLocation createTripDayLocation(Location location) {
		TripDayLocation tripDayLocation = new TripDayLocation();
		tripDayLocation.setLocation(location);
//		tripDayLocation.setTripDay(tripDay);
		return tripDayLocation;
	}

	public static TripStep createTripStep(Location startLocation, Location endLocation, List<TripDirection> tripDirections) {
		TripStep step = new TripStep();
		step.setStartLocation(startLocation);
		step.setEndLocation(endLocation);
		step.setDistance("100 m");
		step.setDistanceUnit(DistanceUnit.METRIC);
		step.setDuration("3 min");
		step.setDirections(tripDirections);
		return step;
	}

	public static TripDirection createTripDirection(Coordinate startCoordinate, Coordinate endCoordinate, String distance, String instruction, String duration) {
		TripDirection tripDirection = new TripDirection();
		tripDirection.setStartCoordinate(startCoordinate);
		tripDirection.setEndCoordinate(endCoordinate);
		tripDirection.setDistance(distance);
		tripDirection.setDuration(duration);
		tripDirection.setInstruction(instruction);
		return tripDirection;
	}

	public static Trip createTestTrip() {
		Location location1 = BaseTestObject.createLocation("New 1", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));
		Location location2 = BaseTestObject.createLocation("New 2", 1.0, 1.1, BaseTestObject.createAddress("Poland", "cracow"));

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
		Trip trip = BaseTestObject.createTrip("Trip 1", new Date(), new Date(), tripDayList);

		return trip;
	}

	public static Location createLocation() {
		return createLocation("SAMPLE_NAME_1", 0.0001, 0.01, createAddress("Poland", "Cracow"));
	}

	public static List<Location> createLocations() {
		List<Location> locations = new ArrayList<>();
		locations.add(createLocation("SAMPLE_NAME_1", 0.0001, 0.01, createAddress("Poland", "Cracow")));
		locations.add(createLocation("SAMPLE_NAME_2", 5.0001, 10.01, createAddress("France", "Paris")));
		return locations;
	}

	public static UserAccount createUserAccount() {
		return createUserAccount("testuser", "test");
	}

}

package pl.edu.agh;

import android.annotation.TargetApi;
import android.os.Build;
import junit.framework.Assert;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;

/**
 * Created by Magda on 2014-12-13.
 */
public class TestTools extends Assert {

	public static void assertEquals(Location location1, Location location2) {
		assertNotNull(location2);
		assertEquals(location1.getName(), location2.getName());
		assertEquals(location1.getDescription(), location2.getDescription());
		assertEquals(location1.getStatus(), location2.getStatus());
//		assertEquals(location1.getCreatedByAccount(), location2.getCreatedByAccount()); // TODO: commented because it can be null ??? test!
		assertEquals(location1.getLatitude(), location2.getLatitude());
		assertEquals(location1.getLongitude(), location2.getLongitude());
		assertEquals(location1.isUsersPrivate(), location2.isUsersPrivate());
		assertEquals(location1.getAddress(), location2.getAddress());
	}

	public static void assertEquals(Address address1, Address address2) {
		assertEquals(address1.getCity(), address2.getCity());
		assertEquals(address1.getCountry(), address2.getCountry());
		assertEquals(address1.getPostalCode(), address2.getPostalCode());
		assertEquals(address1.getStreet(), address2.getStreet());
	}

	public static void assertEquals(UserAccount userAccount1, UserAccount userAccount2) {
		if ( userAccount1 != null && userAccount2 != null ) {
			assertNotNull(userAccount1);
			assertNotNull(userAccount2);
			assertEquals(userAccount1.getLogin(), userAccount2.getLogin());
			assertEquals(userAccount1.getPassword(), userAccount2.getPassword());
			assertEquals(userAccount1.isDefaultUser(), userAccount2.isDefaultUser());
			assertEquals(userAccount1.getToken(), userAccount2.getToken());
			assertEquals(userAccount1.getLanguage(), userAccount2.getLanguage());
		}
	}

	public static void assertEquals(Trip trip1, Trip trip2) {
		assertEquals(trip1.getId(), trip2.getId());
		assertEquals(trip1.getGlobalId(), trip2.getGlobalId());
		assertEquals(trip1.getAuthor(), trip2.getAuthor());
		assertEquals(trip1.getDescription(), trip2.getDescription());
		assertEquals(trip1.getStartDate(), trip2.getStartDate());
		assertEquals(trip1.getEndDate(), trip2.getEndDate());
		// don't test tripDays, because it's a lazy collection
	}

	public static void assertEquals(TripDay tripDay1, TripDay tripDay2) {
		assertEquals(tripDay1.getId(), tripDay2.getId());
		assertEquals(tripDay1.getGlobalId(), tripDay2.getGlobalId());
		assertEquals(tripDay1.getDate(), tripDay2.getDate());
	}

	public static void assertEquals(TripDayLocation tripDayLocation1, TripDayLocation tripDayLocation2) {
		assertEquals(tripDayLocation1.getId(), tripDayLocation2.getId());
		assertEquals(tripDayLocation1.getGlobalId(), tripDayLocation2.getGlobalId());
		assertEquals(tripDayLocation1.getOrdinal(), tripDayLocation2.getOrdinal());
		assertEquals(tripDayLocation2.getLocation(), tripDayLocation2.getLocation());
	}
}

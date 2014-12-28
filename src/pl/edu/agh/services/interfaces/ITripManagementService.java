package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.FormValidationError;

import java.util.List;

/**
 * Created by Magda on 2014-12-04.
 */
public interface ITripManagementService {

	public List<FormValidationError> validateTrip(Trip trip) throws TripException;

	public void saveTripCascade(Trip trip) throws TripException;

	public void saveNewTrip(Trip trip, UserAccount userAccount) throws TripException;

	public void saveTripStep(TripStep step) throws TripException;

	public void saveTripDirection(TripDirection tripDirection) throws TripException;

	public void updateTripCascade(Trip trip) throws TripException;

	public void saveOrUpdateTrip(Trip trip) throws TripException;

	public void saveTrip(Trip trip) throws TripException;

	public void saveTripDay(TripDay tripDay) throws TripException;

	public void saveTripDayLocation(TripDayLocation dayLocation) throws TripException;

	public void updateTrip(Trip trip) throws TripException;

	public void updateTripDay(TripDay tripDay) throws TripException;

	public void updateTripDayLocation(TripDayLocation dayLocation) throws TripException;

	public void updateTripStep(TripStep step) throws TripException;

	public void updateTripDirection(TripDirection tripDirection) throws TripException;

	void deleteTrip(Trip trip) throws TripException;

	void deleteTripDay(TripDay tripDay) throws TripException;

	void deleteTripDayLocation(TripDayLocation tripDayLocation) throws TripException;

	void deleteTripStep(TripStep tripStep) throws TripException;

	void deleteTripDirection(TripDirection tripDirection) throws TripException;

	public List<Trip> getAllTrips() throws TripException;

	public List<Trip> getPastTrips() throws TripException;

	public List<Trip> getCurrentTrips() throws TripException;

	public List<Trip> getFutureTrips() throws TripException;

	public List<Trip> getNewUserTrips(String token) throws TripException;

	public Trip getTripById(long id) throws TripException;

	public Trip getTripByGlobalId(long id) throws TripException;

	public Trip getTripByName(String name) throws TripException;

	public List<TripDirection> getTripDirections(TripStep step) throws TripException;

	public List<TripStep> getTripSteps(TripDay tripDay) throws TripException;

	public List<TripDayLocation> getTripDayLocations(TripDay tripDay) throws TripException;

	public List<TripDay> getTripDays(Trip trip) throws TripException;
}

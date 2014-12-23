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

	public void saveTrip(Trip trip) throws TripException;

	void saveNewTrip(Trip trip, UserAccount userAccount) throws TripException;

	void saveTripDays(Trip trip) throws TripException;

	void saveTripDay(TripDay tripDay) throws TripException;

	public List<Trip> getAllTrips() throws TripException;

	public List<Trip> getPastTrips() throws TripException;

	public List<Trip> getCurrentTrips() throws TripException;

	public List<Trip> getFutureTrips() throws TripException;

	public List<Trip> getNewUserTrips(String token);

	public Trip getTripById(long id) throws TripException;

	public Trip getTripByName(String name) throws TripException;

	public List<TripDirection> getTripDirections(TripStep step) throws TripException;

	public List<TripStep> getTripSteps(TripDay tripDay) throws TripException;

	public List<TripDayLocation> getTripDayLocations(TripDay tripDay) throws TripException;

	public List<TripDay> getTripDays(Trip trip) throws TripException;
}

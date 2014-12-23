package pl.edu.agh.repositories.interfaces;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;

import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public interface ITripRepository {

	public void saveTrip(Trip trip) throws TripException;

	public void saveTripDay(TripDay tripDay) throws TripException;

	public void saveTripDayLocation(TripDayLocation tripDayLocation) throws TripException;

	public void saveTripStep(TripStep tripStep) throws TripException;

	public void saveTripDirection(TripDirection tripDirection) throws TripException;

	public List<Trip> getAllTrips() throws TripException;

	public List<Trip> getPastTrips() throws TripException;

	public List<Trip> getCurrentTrips() throws TripException;

	public List<Trip> getFutureTrips() throws TripException;

	public Trip getTripById(long id) throws TripException;

	public Trip getTripByName(String name) throws TripException;

	public List<TripDirection> getTripDirections(TripStep step) throws TripException;

	List<TripStep> getTripSteps(TripDay day) throws TripException;

	List<TripDayLocation> getTripDayLocations(TripDay tripDay) throws TripException;

	List<TripDay> getTripDays(Trip trip) throws TripException;
}

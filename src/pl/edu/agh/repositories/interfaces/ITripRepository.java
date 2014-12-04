package pl.edu.agh.repositories.interfaces;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;

import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public interface ITripRepository {

	public void saveTrip(Trip trip) throws TripException;

	public List<Trip> getAllTrips() throws TripException;

	public Trip getTripById(long id) throws TripException;

	public Trip getTripByName(String name) throws TripException;

}

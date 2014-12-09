package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.FormValidationError;

import java.util.List;

/**
 * Created by Magda on 2014-12-04.
 */
public interface ITripManagementService {

	public List<FormValidationError> validateTrip(Trip trip) throws TripException;

	public void saveTrip(Trip trip) throws TripException;

	public List<Trip> getAllTrips() throws TripException;

	public List<Trip> getPastTrips() throws TripException;

	public List<Trip> getCurrentTrips() throws TripException;

	public List<Trip> getFutureTrips() throws TripException;

	public Trip getTripById(long id) throws TripException;

	public Trip getTripByName(String name) throws TripException;

}

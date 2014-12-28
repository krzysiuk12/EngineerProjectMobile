package pl.edu.agh.methods;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;

import java.util.Collection;

/**
 * Created by Magda on 2014-12-27.
 */
public abstract class TripDatabaseMethod {

	ITripManagementService tripManagementService;

	ILocationManagementService locationManagementService;

	public TripDatabaseMethod(ITripManagementService tripManagementService, ILocationManagementService locationManagementService) {
		this.tripManagementService = tripManagementService;
		this.locationManagementService = locationManagementService;
	}

	protected abstract void tripMethod(Trip trip) throws TripException;

	protected abstract void tripDayMethod(TripDay tripDay) throws TripException;

	protected abstract void tripDayLocationMethod(TripDayLocation tripDayLocation) throws TripException;

	protected abstract void tripStepMethod(TripStep tripStep) throws TripException;

	protected abstract void tripDirectionMethod(TripDirection tripDirection) throws TripException;

	public ITripManagementService getTripManagementService() {
		return tripManagementService;
	}

	public void setTripManagementService(ITripManagementService tripManagementService) {
		this.tripManagementService = tripManagementService;
	}

	public ILocationManagementService getLocationManagementService() {
		return locationManagementService;
	}

	public void setLocationManagementService(ILocationManagementService locationManagementService) {
		this.locationManagementService = locationManagementService;
	}

	public void performAction(Trip trip) throws TripException {
		performTripAction(trip);
	}

	private void performTripAction(Trip trip) throws TripException {
		tripMethod(trip);
		performTripDayAction(trip);
	}

	private void performTripDayAction(Trip trip) throws TripException {
		new AndroidLogService().debug(trip.getDays() + "");
		for ( TripDay tripDay : trip.getDays() ) {
			tripDay.setTrip(trip);
			performTripDayAction(tripDay);
		}
	}

	private void performTripDayAction(TripDay tripDay) throws TripException {
		Collection<TripDayLocation> locations = tripDay.getLocations();
		Collection<TripStep> tripSteps = tripDay.getTripSteps();
		tripDayMethod(tripDay);

		tripDay.setLocations(null);
		for (TripDayLocation dayLocation : locations) {
			dayLocation.setTripDay(tripDay);
			performTripDayLocationAction(dayLocation);
		}
		tripDay.setLocations(locations);

		if ( tripSteps != null ) {
			tripDay.setTripSteps(null);
			for (TripStep step : tripSteps) {
				step.setTripDay(tripDay);
				performTripStepAction(step);

			}
			tripDay.setTripSteps(tripSteps);
		}
	}

	public void performTripDayLocationAction(TripDayLocation dayLocation) throws TripException {
		try {
			locationManagementService.saveOrUpdateLocation(dayLocation.getLocation());  // save if location doesn't exist in local database
			Location location = locationManagementService.getLocationByGlobalId(dayLocation.getLocation().getGlobalId());
			dayLocation.setLocation(location);
		} catch (LocationException e) {
			e.printStackTrace();
			throw new TripException(e);
		}
		tripDayLocationMethod(dayLocation);
	}

	public void performTripStepAction(TripStep step) throws TripException {
		Collection<TripDirection> directions = step.getDirections();
		tripStepMethod(step);
		if ( directions != null ) {
			step.setDirections(null);
			for (TripDirection direction : directions) {
				direction.setTripStep(step);
				tripDirectionMethod(direction);
			}
			step.setDirections(directions);
		}
	}
}

package pl.edu.agh.methods;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;

/**
 * Created by Magda on 2014-12-27.
 */
public class SaveTripDatabaseMethod extends TripDatabaseMethod {

	public SaveTripDatabaseMethod(ITripManagementService tripRepository, ILocationManagementService locationManagementService) {
		super(tripRepository, locationManagementService);
	}

	@Override
	protected void tripMethod(Trip trip) throws TripException {
		getTripManagementService().saveTrip(trip);
	}

	@Override
	protected void tripDayMethod(TripDay tripDay) throws TripException {
		getTripManagementService().saveTripDay(tripDay);
	}

	@Override
	protected void tripDayLocationMethod(TripDayLocation tripDayLocation) throws TripException {
		getTripManagementService().saveTripDayLocation(tripDayLocation);
	}

	@Override
	protected void tripStepMethod(TripStep tripStep) throws TripException {
		getTripManagementService().saveTripStep(tripStep);
	}

	@Override
	protected void tripDirectionMethod(TripDirection tripDirection) throws TripException {
		getTripManagementService().saveTripDirection(tripDirection);
	}
}

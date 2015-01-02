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
 * Created by Magda on 2014-12-28.
 */
public class DeleteTripDatabaseMethod extends TripDatabaseMethod {

	public DeleteTripDatabaseMethod(ITripManagementService tripManagementService, ILocationManagementService locationManagementService) {
		super(tripManagementService, locationManagementService);
	}

	@Override
	protected void tripMethod(Trip trip) throws TripException {
		tripManagementService.deleteTrip(trip);
	}

	@Override
	protected void tripDayMethod(TripDay tripDay) throws TripException {
		tripManagementService.deleteTripDay(tripDay);
	}

	@Override
	protected void tripDayLocationMethod(TripDayLocation tripDayLocation) throws TripException {
		tripManagementService.deleteTripDayLocation(tripDayLocation);
	}

	@Override
	protected void tripStepMethod(TripStep tripStep) throws TripException {
		tripManagementService.deleteTripStep(tripStep);
	}

	@Override
	protected void tripDirectionMethod(TripDirection tripDirection) throws TripException {
		tripManagementService.deleteTripDirection(tripDirection);
	}

}

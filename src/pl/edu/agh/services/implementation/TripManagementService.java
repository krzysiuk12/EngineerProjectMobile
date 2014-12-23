package pl.edu.agh.services.implementation;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.repositories.implementation.OrmLiteTripRepository;
import pl.edu.agh.repositories.interfaces.ITripRepository;
import pl.edu.agh.services.interfaces.ITripManagementService;
import pl.edu.agh.tools.StringTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Magda on 2014-12-04.
 */
public class TripManagementService extends BaseService implements ITripManagementService {

	private ITripRepository tripRepository;

	@Deprecated
	public TripManagementService() {
		tripRepository = new OrmLiteTripRepository(getHelper());
	}

	public TripManagementService(Context context) {
		tripRepository = new OrmLiteTripRepository(getHelperInternal(context));
	}

	public TripManagementService(OrmLiteSqliteOpenHelper helper) {
		tripRepository = new OrmLiteTripRepository(helper);
	}

	@Override
	public List<FormValidationError> validateTrip(Trip trip) throws TripException {
		List<FormValidationError> errors = new ArrayList<>();
		if ( StringTools.isNullOrEmpty(trip.getName()) ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_NAME_IS_REQUIRED.getStringResourceId()));
		}
		if ( trip.getStartDate() == null ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_START_DATE_IS_REQUIRED.getStringResourceId()));
		}
		if ( trip.getEndDate() == null ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_END_DATE_IS_REQUIRED.getStringResourceId()));
		}
		if ( trip.getStartDate().after(trip.getEndDate()) ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_START_DATE_BEFORE_END_DATE.getStringResourceId()));
		}
		if ( trip.getDays() == null || trip.getDays().isEmpty() ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAYS_IS_REQUIRED.getStringResourceId()));
		}

		return errors;
	}

	@Override
	public void saveTrip(Trip trip) throws TripException {
		List<FormValidationError> errors = validateTrip(trip);
		if ( !errors.isEmpty() ) {
			throw new TripException(errors);
		}
		saveTripDays(trip);
		tripRepository.saveTrip(trip);
	}

	@Override
	public void saveNewTrip(Trip trip, UserAccount userAccount) throws TripException {
		trip.setAuthor(userAccount);

		List<FormValidationError> errors = validateTrip(trip);
		if ( !errors.isEmpty() ) {
			throw new TripException(errors);
		}

		tripRepository.saveTrip(trip);
	}
	@Override
	public void saveTripDays(Trip trip) throws TripException {
		for ( TripDay tripDay : trip.getDays() ) {
			tripDay.setTrip(trip);
			saveTripDay(tripDay);
		}
	}

	@Override
	public void saveTripDay(TripDay tripDay) throws TripException {
		Collection<TripDayLocation> locations = tripDay.getLocations();
		Collection<TripStep> tripSteps = tripDay.getTripSteps();
		tripRepository.saveTripDay(tripDay);

		tripDay.setLocations(null);
		for (TripDayLocation dayLocation : locations) {
			dayLocation.setTripDay(tripDay);
			tripRepository.saveTripDayLocation(dayLocation);
			// TODO: saving locations if they don't exist in DB
		}
		tripDay.setLocations(locations);

		if ( tripSteps != null ) {
			tripDay.setTripSteps(null);
			for (TripStep step : tripSteps) {
				step.setTripDay(tripDay);
				saveTripStep(step);

			}
			tripDay.setTripSteps(tripSteps);
		}
	}

	public void saveTripStep(TripStep step) throws TripException {
		Collection<TripDirection> directions = step.getDirections();
		tripRepository.saveTripStep(step);
		if ( directions != null ) {
			step.setDirections(null);
			for (TripDirection direction : directions) {
				direction.setTripStep(step);
				tripRepository.saveTripDirection(direction);
			}
			step.setDirections(directions);
		}
	}


	@Override
	public List<Trip> getAllTrips() throws TripException {
		return tripRepository.getAllTrips();
	}

	@Override
	public List<Trip> getPastTrips() throws TripException {
		return tripRepository.getPastTrips();
	}

	@Override
	public List<Trip> getCurrentTrips() throws TripException {
		return tripRepository.getCurrentTrips();
	}

	@Override
	public List<Trip> getFutureTrips() throws TripException {
		return tripRepository.getFutureTrips();
	}

	@Override
	public List<Trip> getNewUserTrips(String token) {
		return null;
	}

	@Override
	public Trip getTripById(long id) throws TripException {
		return tripRepository.getTripById(id);
	}

	@Override
	public Trip getTripByName(String name) throws TripException {
		return tripRepository.getTripByName(name);
	}

	@Override
	public List<TripDirection> getTripDirections(TripStep step) throws TripException {
		return tripRepository.getTripDirections(step);
	}

	@Override
	public List<TripStep> getTripSteps(TripDay tripDay) throws TripException {
		return tripRepository.getTripSteps(tripDay);
	}

	@Override
	public List<TripDayLocation> getTripDayLocations(TripDay tripDay) throws TripException {
		return tripRepository.getTripDayLocations(tripDay);
	}

	@Override
	public List<TripDay> getTripDays(Trip trip) throws TripException {
		return tripRepository.getTripDays(trip);
	}

}

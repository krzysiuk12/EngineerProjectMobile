package pl.edu.agh.services.implementation;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.repositories.implementation.OrmLiteTripRepository;
import pl.edu.agh.repositories.interfaces.ITripRepository;
import pl.edu.agh.services.interfaces.ILocationManagementService;
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
	private ILocationManagementService locationManagementService;

	@Deprecated
	public TripManagementService() {
		tripRepository = new OrmLiteTripRepository(getHelper());
	}

	public TripManagementService(Context context) {
		tripRepository = new OrmLiteTripRepository(getHelperInternal(context));
		locationManagementService = new LocationManagementService(context);
	}

	public TripManagementService(OrmLiteSqliteOpenHelper helper) {
		tripRepository = new OrmLiteTripRepository(helper);
		locationManagementService = new LocationManagementService(helper);
	}

	// <editor-fold desc="Validation">

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
		if ( trip.getStartDate() != null && trip.getEndDate() != null && trip.getStartDate().after(trip.getEndDate()) ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_START_DATE_BEFORE_END_DATE.getStringResourceId()));
		}
		if ( trip.getDays() == null || trip.getDays().isEmpty() ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAYS_IS_REQUIRED.getStringResourceId()));
		}

		if ( trip.getDays() != null ) {
			for ( TripDay tripDay : trip.getDays() ) {
				errors.addAll(validateTripDay(tripDay, trip));
			}
		}

		return errors;
	}

	public List<FormValidationError> validateTripDay(TripDay tripDay, Trip trip) {
		List<FormValidationError> errors = new ArrayList<>();
		if ( tripDay.getDate() == null ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_END_DATE_IS_REQUIRED.getStringResourceId()));
		}
		if ( tripDay.getDate() != null && trip.getStartDate() != null && trip.getEndDate() != null &&
				(tripDay.getDate().before(trip.getStartDate()) || tripDay.getDate().after(trip.getEndDate())) ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_INVALID_DATE.getStringResourceId()));
		}
		if ( tripDay.getLocations() == null || tripDay.getLocations().isEmpty() ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_LOCATIONS_ARE_REQUIRED.getStringResourceId()));
		}
		return errors;
	}

	public List<FormValidationError> validateTripDayLocation(TripDayLocation tripDayLocation) {
		List<FormValidationError> errors = new ArrayList<>();
		if ( tripDayLocation.getLocation() == null ) {
			errors.add(new FormValidationError(TripException.PredefinedExceptions.VALIDATION_TRIP_DAY_LOCATION_LOCATION_IS_REQUIRED.getStringResourceId()));
		}

		return errors;
	}

	// </editor-fold>

	// <editor-fold desc="Save Trip">

	@Override
	public void saveTrip(Trip trip) throws TripException {
		List<FormValidationError> errors = validateTrip(trip);
		if ( !errors.isEmpty() ) {
			throw new TripException(errors);
		}
		tripRepository.saveTrip(trip);
		saveTripDays(trip);
	}

	@Override
	public void saveNewTrip(Trip trip, UserAccount userAccount) throws TripException {
		trip.setAuthor(userAccount);

		saveTrip(trip);
	}

	@Override
	public void updateTrip(Trip trip) throws TripException {
		tripRepository.updateTrip(trip);
	}

	@Override
	public void saveOrUpdateTrip(Trip trip) throws TripException {
		Trip tripInDatabase = getTripByGlobalId(trip.getGlobalId());
		if ( tripInDatabase == null ) {
			saveTrip(trip);
		} else {
			updateTrip(trip);
		}
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
			saveTripDayLocation(dayLocation);
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

	@Override
	public void saveTripDayLocation(TripDayLocation dayLocation) throws TripException {
		try {
			locationManagementService.saveOrUpdateLocation(dayLocation.getLocation());  // save if location doesn't exist in local database
			Location location = locationManagementService.getLocationByGlobalId(dayLocation.getLocation().getGlobalId());
			dayLocation.setLocation(location);
		} catch (LocationException e) {
			e.printStackTrace();
			throw new TripException(e);
		}
		tripRepository.saveTripDayLocation(dayLocation);
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

	// </editor-fold>

	// <editor-fold desc="Get Trips">

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
	public List<Trip> getNewUserTrips(String token) throws TripException {
		return tripRepository.getNewUserTrips(token);
	}

	// </editor-fold>

	@Override
	public Trip getTripById(long id) throws TripException {
		return tripRepository.getTripById(id);
	}

	@Override
	public Trip getTripByGlobalId(long id) throws TripException {
		return tripRepository.getTripByGlobalId(id);
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

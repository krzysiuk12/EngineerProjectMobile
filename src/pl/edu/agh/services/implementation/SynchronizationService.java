package pl.edu.agh.services.implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.GetAllLocationsInAreaAsyncTask;
import pl.edu.agh.asynctasks.locations.GetAllLocationsInAreaInScopeAsyncTask;
import pl.edu.agh.asynctasks.locations.GetAllPrivateLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewLocationAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewPrivateLocationAsyncTask;
import pl.edu.agh.asynctasks.trips.GetAllTripDayDetailsAsyncTask;
import pl.edu.agh.asynctasks.trips.GetMyTripsAsyncTask;
import pl.edu.agh.asynctasks.trips.PostAddTripAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.serializers.TripCreationSerializer;
import pl.edu.agh.serializers.TripDayCreationSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ISynchronizationService;
import pl.edu.agh.services.interfaces.ITripManagementService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * Created by Magda on 2014-11-21.
 */
public class SynchronizationService extends BaseService implements ISynchronizationService {

	ILocationManagementService locationManagementService;
	ITripManagementService tripManagementService;
	IBinder binder = new LocalBinder();

	public SynchronizationService(Context context) {
		tripManagementService = new TripManagementService(context);
		locationManagementService = new LocationManagementService(context);
	}

	public SynchronizationService(OrmLiteSqliteOpenHelper openHelper) {
		tripManagementService = new TripManagementService(openHelper);
		locationManagementService = new LocationManagementService(openHelper);
	}

	// <editor-fold description="Downloading Locations">

	// TODO : remove, testing purposes (small db)
	@Override
	public void downloadAllLocations() {
		getLogService().debug("downloadAllLocations start");
		List<Location> locations = new ArrayList<Location>();
		try {
			locations = new GetAllLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if ( locations == null )
			return;

		for ( Location location : locations ){
			try {
				locationManagementService.saveOrUpdateLocation(location);
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
		getLogService().debug("downloadAllLocations end");
	}

	@Override
	public void downloadAllPrivateLocations() {
		List<Location> locations = null;
		try {
			locations = new GetAllPrivateLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if ( locations == null )
			return;

		for ( Location location : locations ) {
			location.setCreatedByAccount(UserAccountManagementService.getUserAccount());
			try {
				locationManagementService.saveOrUpdateLocation(location);
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void downloadLocationsInScope(double latitude, double longitude, double scope) {
		List<Location> locations = null;

		try {
			if ( scope != 0.0 ) {
				locations = new GetAllLocationsInAreaInScopeAsyncTask(UserAccountManagementService.getToken(), latitude, longitude, scope).execute().get();
			} else {
				locations = new GetAllLocationsInAreaAsyncTask(UserAccountManagementService.getToken(), latitude, longitude).execute().get();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if ( locations == null )
			return;

		for ( Location location : locations ) {
			try {
//				locationManagementService.saveLocation(location);
				locationManagementService.saveOrUpdateLocation(location);
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}

	}

	// </editor-fold>

	// <editor-fold desc="Downloading trips">

	@Override
	public void downloadTrips(String token) {
		getLogService().debug("SynchronizationService", "downloadTrips start");
		ArrayList<Trip> trips = new ArrayList<>();
		try {
			List<Trip> tripList = new GetMyTripsAsyncTask(token).execute().get();
			trips = new ArrayList<>(tripList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		getLogService().debug("SynchronizationService", "downloaded trips: " + trips.size());
		if ( !trips.isEmpty() ) {
			for ( Trip trip : trips ) {
				downloadTripDetails(trip);
			}
		}

		for ( Trip trip : trips ) {
			trip.setAuthor(UserAccountManagementService.getUserAccount());  // TODO: move up to create downloadTripsForUser
			try {
				tripManagementService.saveOrUpdateTrip(trip);
			} catch (TripException e) {
				e.printStackTrace();
			}
		}
		getLogService().debug("SynchronizationService", "downloadTrips end");
	}

	public void downloadTripDetails(Trip trip) {
		if ( trip.getDays() != null ) {
			List<TripDay> newTripDays = new ArrayList<>();
			for ( TripDay day : trip.getDays() ) {
				try {
					TripDay allTripDayData = new GetAllTripDayDetailsAsyncTask(UserAccountManagementService.getToken(), day.getGlobalId()).execute().get();
					newTripDays.add(allTripDayData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			trip.setDays(newTripDays);
		}
	}

	// </editor-fold>

	// <editor-fold desc="Sending Locations">

	@Override
	public void sendNewPublicLocations() throws SynchronizationException {
		List<Location> locations = null;
		try {
			locations = locationManagementService.getAllNewPublicLocations();
		} catch (LocationException e) {
			e.printStackTrace();
		}
		if ( locations == null )
			return;
		getLogService().debug("SynchronizationService", locations.size() + " ");

		for ( Location location : locations) {
			try {
				ResponseSerializer<Long> response = new PostAddNewLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
				if ( validateServerResponse(response) && response.getResult() != null ) {
					location.setGlobalId(response.getResult());
					location.setSynced(true);
					locationManagementService.updateLocation(location);
				} else {
					throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_SIDE_ERROR);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();    // TODO: error handling
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (LocationException e) {
				throw new SynchronizationException(SynchronizationException.PredefinedExceptions.DATABASE_ERROR);
			}
		}
	}

	@Override
	public void sendNewPrivateLocations() throws SynchronizationException {
		List<Location> locations = null;
		getLogService().debug("sendNewPrivateLocations()");
		try {
			locations = locationManagementService.getAllNewPrivateLocations();
		} catch (LocationException e) {
			getLogService().error(getClass().getName(), e.toString());
		}
		if ( locations == null )
			return;

		for ( Location location : locations) {
			try {
				ResponseSerializer<Long> response = new PostAddNewPrivateLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
				if ( validateServerResponse(response) && response.getResult() != null ) {
					location.setGlobalId(response.getResult());
					location.setSynced(true);
					locationManagementService.updateLocation(location);
				} else {
					// no result returned by server
					throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_SIDE_ERROR);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (LocationException e) {
				throw new SynchronizationException(SynchronizationException.PredefinedExceptions.DATABASE_ERROR);
			}
		}
	}

	@Override
	public void sendAllNewLocations() throws SynchronizationException {
		sendNewPrivateLocations();
		sendNewPublicLocations();
	}

	// </editor-fold>

	// <editor-fold desc="Sending Trips">

	private void addTestTrip() throws TripException {

		// TripDayLocations
		TripDayLocation tripDayLocation1 = new TripDayLocation();
		TripDayLocation tripDayLocation2 = new TripDayLocation();
		try {
			Location location1 = locationManagementService.getLocationByGlobalId(1L);
			tripDayLocation1.setLocation(location1);
			tripDayLocation1.setOrdinal(1);

			Location location2 = locationManagementService.getLocationByGlobalId(2L);
			tripDayLocation2.setLocation(location2);
			tripDayLocation2.setOrdinal(2);
		} catch (LocationException e) {
			e.printStackTrace();
		}
		List<TripDayLocation> tripDayLocationList = new ArrayList<>();
		tripDayLocationList.add(tripDayLocation1);
		tripDayLocationList.add(tripDayLocation2);

		// TripDay
		TripDay tripDay1 = new TripDay();
		tripDay1.setDate(new Date());
		tripDay1.setLocations(tripDayLocationList);

		List<TripDay> tripDayList = new ArrayList<>();
		tripDayList.add(tripDay1);

		// Trip
		Trip trip = new Trip();
		trip.setName("name");
		trip.setDescription("desc");
		trip.setDays(tripDayList);
		trip.setStartDate(new Date());
		trip.setEndDate(new Date());
		trip.setAuthor(UserAccountManagementService.getUserAccount());
		trip.setSynced(false);
		trip.setTravelMode(TravelMode.WALKING);
		trip.setDistanceUnit(DistanceUnit.METRIC);

		tripManagementService.saveTripCascade(trip);
	}

	@Override
	public void sendTrips(String token) throws SynchronizationException {
		getLogService().debug("sendTrips - start");
		try {
			addTestTrip();  // TODO: REMOVE
		} catch (TripException e) {
			throw new SynchronizationException(e.getFormValidationErrors());
		}
		List<Trip> trips = null;
		try {
			trips = tripManagementService.getNewUserTrips(token);
		} catch (TripException e) {
			e.printStackTrace();
		}
		if ( trips == null )
			return;

		for ( Trip trip : trips ) {
			TripCreationSerializer tripCreationSerializer = buildTripCreationSerializer(trip);
			try {
				ResponseSerializer<Trip> response = new PostAddTripAsyncTask(UserAccountManagementService.getToken(), tripCreationSerializer).execute().get();

				if ( validateServerResponse(response) ) {
					Trip tripFromServer = response.getResult();

					tripFromServer.setId(trip.getId());
					tripFromServer.setAuthor(trip.getAuthor());
					tripFromServer.setSynced(true);

					Map<Date, Integer> tripDayToIdMap = buildDayToIdMap(trip.getDays());
					getLogService().debug(tripDayToIdMap.toString());

					tripManagementService.updateTripCascade(tripFromServer);

					downloadTripDetails(tripFromServer);
					updateTripDayIds(tripFromServer.getDays(), tripDayToIdMap);

					tripManagementService.updateTripCascade(tripFromServer);

					getLogService().debug("updated trip: " + tripFromServer);
				} else {
					throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_SIDE_ERROR);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TripException e) {
				throw new SynchronizationException(SynchronizationException.PredefinedExceptions.DATABASE_ERROR);
			}
		}

		// TODO: test

		getLogService().debug("sendTrips - end");
	}

	private Map<Date, Integer> buildDayToIdMap(Collection<TripDay> tripDays) {
		Map<Date, Integer> tripDayToIdMap = new HashMap<>();
		for ( TripDay tripDay : tripDays ) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tripDay.getDate());
			// todo: dirty hack, test if it always works
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 19);
			tripDayToIdMap.put(calendar.getTime(), tripDay.getId());
		}
		return tripDayToIdMap;
	}

	private void updateTripDayIds(Collection<TripDay> tripDays, Map<Date, Integer> tripDayToIdMap) {
		for ( TripDay tripDay : tripDays ) {
			getLogService().debug(tripDay.getDate().toString());
			tripDay.setId(tripDayToIdMap.get(tripDay.getDate()));
		}
	}

	private TripCreationSerializer buildTripCreationSerializer(Trip trip) {
		List<TripDayCreationSerializer> tripDayCreationSerializers = new ArrayList<>();
		for ( TripDay tripDay : trip.getDays() ) {
			if ( tripDay.getLocations() != null ) {
				tripDayCreationSerializers.add(new TripDayCreationSerializer(new ArrayList<TripDayLocation>(tripDay.getLocations())));
			}
		}
		TripCreationSerializer tripCreationSerializer = new TripCreationSerializer(
				trip.getName(),
				trip.getDescription(),
				trip.getStartDate(),
				trip.getTravelMode(),
				trip.getDistanceUnit(),
				tripDayCreationSerializers);

		return tripCreationSerializer;
	}

	// </editor-fold>


	public class LocalBinder extends Binder {
		public SynchronizationService getService() {
			return SynchronizationService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

}

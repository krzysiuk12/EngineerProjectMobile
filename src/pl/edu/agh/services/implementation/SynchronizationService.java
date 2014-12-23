package pl.edu.agh.services.implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.GetAllPrivateLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewLocationAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewPrivateLocationAsyncTask;
import pl.edu.agh.asynctasks.trips.GetAllTripDayDetailsAsyncTask;
import pl.edu.agh.asynctasks.trips.GetMyTripsAsyncTask;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.ToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.repositories.implementation.OrmLiteTripRepository;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ISynchronizationService;
import pl.edu.agh.services.interfaces.ITripManagementService;

import java.util.ArrayList;
import java.util.List;
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
				locationManagementService.saveLocation(location);
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
				locationManagementService.saveLocation(location);
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void downloadLocationsInScope(double latitude, double longitute) {
		// TODO: main download method - uses map!
	}

	// </editor-fold>

	// <editor-fold="Downloading trips">

	@Override
	public void downloadTrips() {
		getLogService().debug("SynchronizationService", "downloadTrips start");
		ArrayList<Trip> trips = new ArrayList<>();
		try {
			List<Trip> tripList = new GetMyTripsAsyncTask(UserAccountManagementService.getToken()).execute().get();
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
				tripManagementService.saveTrip(trip);
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
					TripDay allTripDayData = new GetAllTripDayDetailsAsyncTask(UserAccountManagementService.getToken(), day.getId()).execute().get();
					newTripDays.add(allTripDayData);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				getLogService().debug(day.toString());
			}
			trip.setDays(newTripDays);
		}
	}

	// </editor-fold>

	// <editor-fold description="Sending Locations">

	@Override
	public void sendNewPublicLocations() {
		List<Location> locations = null;
		try {
			locations = locationManagementService.getAllNewPublicLocations();
		} catch (LocationException e) {
			e.printStackTrace();
		}
		getLogService().debug("SynchronizationService", locations.size() + " ");
		if ( locations == null )
			return; // TODO: toast if nothing to send

		for ( Location location : locations) {
			try {
				ResponseSerializer response = new PostAddNewLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
				if ( response.getStatus() == ResponseStatus.OK ) {
					location.setSynced(true);
					locationManagementService.updateLocation(location);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();    // TODO: error handling
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendNewPrivateLocations() {
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
				ResponseSerializer response = new PostAddNewPrivateLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
				if ( response.getStatus() == ResponseStatus.OK ) {
					location.setSynced(true);
					locationManagementService.updateLocation(location);
				} else {
//					new ErrorToastBuilder(getApplicationContext(), )
					// TODO: error handling
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendAllNewLocations() {
		sendNewPrivateLocations();
		sendNewPublicLocations();
	}

	// </editor-fold>

	@Override
	public void sendTrips() {
		// TODO: sending trips
	}

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

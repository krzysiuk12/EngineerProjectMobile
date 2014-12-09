package pl.edu.agh.services.implementation;

import android.content.Context;
import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewLocationAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewPrivateLocationAsyncTask;
import pl.edu.agh.asynctasks.trips.GetMyTripsAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.TripException;
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
	OrmLiteLocationRepository locationRepository;
	OrmLiteTripRepository tripRepository;

	public SynchronizationService() {
		super();
		tripManagementService = new TripManagementService();
		locationManagementService = new LocationManagementService();
		locationRepository = new OrmLiteLocationRepository(getHelper());
		tripRepository = new OrmLiteTripRepository(getHelper());
	}

	public SynchronizationService(Context context) {
		tripManagementService = new TripManagementService(context);
		locationManagementService = new LocationManagementService(context);
	}

	public SynchronizationService(OrmLiteLocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	public SynchronizationService(OrmLiteTripRepository tripRepository) {
		this.tripRepository = tripRepository;
	}

	// TODO : remove, testing purposes (small db)
	@Override
	public void downloadAllLocations() {
		ArrayList<Location> locationList = new ArrayList<Location>();
		try {
			List<Location> locations = new GetAllLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
			if ( locations != null )
				locationList = new ArrayList<Location>(locations);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		for ( Location location : locationList ){
			try {
				locationManagementService.saveLocation(location);
			} catch (LocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void downloadTrips() {
		getLogService().debug("SynchronizationService", "start downloadTrips()");
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
		if ( !trips.isEmpty() )
			getLogService().debug((trips.get(0)).toString());

		for ( Trip trip : trips ) {
			try {
				new TripManagementService().saveTrip(trip);
//				new TripManagementService(OpenHelperManager.getHelper(this, TestDatabaseHelper.class));
//				new TripManagementService(getHelper()).saveTrip(trip);
			} catch (TripException e) {
				e.printStackTrace();
			}
		}
		getLogService().debug("SynchronizationService", "end downloadTrips()");
	}

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
			e.printStackTrace();
		}
		if ( locations == null )
			return;

		for ( Location location : locations) {
			try {
				ResponseSerializer response = new PostAddNewPrivateLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
				if ( response.getStatus() == ResponseStatus.OK ) {
					location.setSynced(true);
					locationManagementService.updateLocation(location);
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

}

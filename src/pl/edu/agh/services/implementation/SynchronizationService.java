package pl.edu.agh.services.implementation;

import android.content.Intent;
import android.os.IBinder;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewLocationAsyncTask;
import pl.edu.agh.asynctasks.locations.PostAddNewPrivateLocationAsyncTask;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.services.interfaces.ISynchronizationService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Magda on 2014-11-21.
 */
public class SynchronizationService extends OrmLiteBaseService<TestDatabaseHelper> implements ISynchronizationService{

	OrmLiteLocationRepository locationRepository;

	public SynchronizationService(OrmLiteLocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	// TODO : remove, testing purposes (small db)
	// todo: use location management service for this ?
	@Override
	public void downloadAllLocations() {
		ArrayList<Location> locationList = new ArrayList<Location>();
		try {
			List<Location> locations = new GetAllLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
			locationList = new ArrayList<Location>(locations);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		for ( Location location : locationList ){
			locationRepository.saveLocation(location);  // TODO: save / saveOrUpdate
		}
	}

	@Override
	public void sendNewPublicLocations() {
		List<Location> locations = null;
		try {
			locations = locationRepository.getAllNewPublicLocations();
		} catch (LocationException e) {
			e.printStackTrace();
		}
		if ( locations == null )
			return;

		for ( Location location : locations) {
			try {
				new PostAddNewLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
			} catch (InterruptedException e) {
				e.printStackTrace();    // TODO: error handling
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendNewPrivateLocations() {
		List<Location> locations = null;
		try {
			locations = locationRepository.getAllNewPrivateLocations();
		} catch (LocationException e) {
			e.printStackTrace();
		}
		if ( locations == null )
			return;

		for ( Location location : locations) {
			try {
				new PostAddNewPrivateLocationAsyncTask(UserAccountManagementService.getToken(), location).execute().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;    // TODO!
	}
}

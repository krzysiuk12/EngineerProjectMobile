package pl.edu.agh.services.implementation;

import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Magda on 2014-11-21.
 */
public class SynchronizationService {

	OrmLiteLocationRepository locationRepository;

	public SynchronizationService(OrmLiteLocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	// TODO : remove, testing purposes (small db)
	// todo: use location management service for this ?
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

}

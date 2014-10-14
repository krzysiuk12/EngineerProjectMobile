package pl.edu.agh.fragments;

import android.content.Intent;
import pl.edu.agh.activities.LocationDescriptionActivity;
import pl.edu.agh.asynctasks.locations.GetAllLocationsAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Magdalena Strzoda on 2014-06-15.
 */
public class LocationsListFragment extends AbstractListFragment<Location> {

	public LocationsListFragment() {
		super();
		detailsPaneId = R.id.ShowLocations_LocationDescription; // required
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(Location location, int index) {
		return LocationDescriptionFragment.newInstance(location, index);
	}

	@Override
	protected void setClassForDetailsIntent(Intent intent) {
		intent.setClass(getActivity(), LocationDescriptionActivity.class);
	}

	@Override
	protected void setListAdapter() {
		//TODO: replace with local db
		ArrayList<Location> locationList = new ArrayList<Location>();
		try {
			List<Location> locations = new GetAllLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
			locationList = new ArrayList<Location>(locations);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		setListAdapter(new LocationAdapter(getActivity().getApplicationContext(), locationList));
	}

}

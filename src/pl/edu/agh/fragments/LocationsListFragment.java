package pl.edu.agh.fragments;

import pl.edu.agh.activities.LocationDescriptionActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magdalena Strzoda on 2014-06-15.
 */
public class LocationsListFragment extends AbstractListFragment<Location> {

	private int detailsPaneId;

	public LocationsListFragment() {
		super();
		detailsPaneId = R.id.ShowLocations_LocationDescription; // required
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(Location location, int index) {
		return LocationDescriptionFragment.newInstance(location, index);
	}

	@Override
	protected Class getClassForDetailsIntent() {
		return LocationDescriptionActivity.class;
	}

	@Override
	protected AbstractAdapter getAdapterInstance() {

		// TODO : proper call to the database
		// TODO : null pointer checks
		List<Location> locationList = (new OrmLiteLocationRepository(new TestDatabaseHelper(getActivity()))).getAllLocations();
		return new LocationAdapter(getActivity(), (ArrayList) locationList);
	}

	protected int getDetailsPaneId() {
		return detailsPaneId;
	}

}

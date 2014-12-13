package pl.edu.agh.fragments;

import pl.edu.agh.activities.LocationDescriptionActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.main.R;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magdalena Strzoda on 2014-06-15.
 */
public class LocationsListFragment extends AbstractListFragment<Location> {

	private int detailsPaneId;
	private ILocationManagementService locationManagementService;

	public LocationsListFragment() {
		super();
		locationManagementService = new LocationManagementService(getActivity());
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

		List<Location> locations = new ArrayList<>();
		try {
			List<Location> locationsFromDatabase = locationManagementService.getAllLocations();
			if ( locationsFromDatabase != null )
				locations = locationsFromDatabase;
		} catch (LocationException e) {
			// TODO: error handling
			e.printStackTrace();
		}
		return new LocationAdapter(getActivity(), (ArrayList) locations);
	}

	protected int getDetailsPaneId() {
		return detailsPaneId;
	}

}

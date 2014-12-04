package pl.edu.agh.fragments;

import pl.edu.agh.domain.trips.Trip;

/**
 * Created by Magda on 2014-12-03.
 */
public class TripsListFragment extends AbstractListFragment<Trip> {

	@Override
	protected AbstractAdapter getAdapterInstance() {
		return null;
	}

	@Override
	protected Class getClassForDetailsIntent() {
		return null;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(Trip listItem, int index) {
		return null;
	}

	@Override
	protected int getDetailsPaneId() {
		return 0;
	}

}

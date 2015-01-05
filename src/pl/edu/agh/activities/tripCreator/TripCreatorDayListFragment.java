package pl.edu.agh.activities.tripCreator;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.AbstractListFragment;
import pl.edu.agh.fragments.TripDayAdapter;

import java.util.ArrayList;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayListFragment extends AbstractListFragment<TripDay> {

	private Trip trip;

	public static TripCreatorDayListFragment newInstance(Trip trip) {
		TripCreatorDayListFragment fragment = new TripCreatorDayListFragment();
		fragment.trip = trip;
		return fragment;
	}

	@Override
	protected AbstractAdapter getAdapterInstance() {
		ArrayList<TripDay> tripDays = new ArrayList<>(trip.getDays());
		return new TripDayAdapter(getActivity(), tripDays);
	}

	@Override
	protected Class getClassForDetailsIntent() {
		return TripCreatorDayLocationsActivity.class;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(TripDay listItem, int index) {
		return TripCreatorDayLocationsFragment.newInstance(listItem, index);
	}

	@Override
	protected int getDetailsPaneId() {
		return 0;
	}

}

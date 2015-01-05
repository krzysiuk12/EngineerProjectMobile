package pl.edu.agh.activities.tripCreator;

import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.fragments.AbstractDescriptionFragment;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayLocationsFragment extends AbstractDescriptionFragment<TripDay> {

	public static TripCreatorDayLocationsFragment newInstance(TripDay tripDay, long index) {
		TripCreatorDayLocationsFragment fragment = new TripCreatorDayLocationsFragment();
		fragment.setInitialArguments(index, tripDay);
		return fragment;
	}

	@Override
	protected int getLayoutId() {
		return 0;
	}

	@Override
	protected void showDetails() {

	}
}

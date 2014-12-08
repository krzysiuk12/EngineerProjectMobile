package pl.edu.agh.fragments;

import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

/**
 * Created by Magda on 2014-12-04.
 */
public class TripDetailsFragment extends AbstractDescriptionFragment<Trip> {

	public static TripDetailsFragment newInstance(Trip trip, long index) {
		TripDetailsFragment fragment = new TripDetailsFragment();
		fragment.setInitialArguments(index, trip);
		return fragment;
	}


	@Override
	protected int getLayoutId() {
		return R.layout.trip_details_fragment;
	}

	@Override
	protected void showDetails() {
		Trip trip = (Trip) getArguments().getSerializable(KEY_ITEM);

		if ( trip != null ) {
			RenderingTools.setTextViewText(view, R.id.TripDetails_Trip_Name, trip.getName());
			// TODO : fill when layout created
		}
	}

}
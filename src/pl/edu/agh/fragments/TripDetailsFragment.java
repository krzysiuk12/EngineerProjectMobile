package pl.edu.agh.fragments;

import android.content.Intent;
import android.view.View;
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
			RenderingTools.setTextViewText(view, R.id.ShowTrips_TripDetails_StartDate, RenderingTools.formatDate(trip.getStartDate()));
			RenderingTools.setTextViewText(view, R.id.ShowTrips_TripDetails_EndDate, RenderingTools.formatDate(trip.getEndDate()));
			RenderingTools.setTextViewText(view, R.id.TripDetails_Trip_Description, trip.getDescription());
			// TODO : fill when layout created
		}
	}

	public void onTripDayDetailsButtonClicked(View view) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), TripDayDetailsFragment.class);
	}

}

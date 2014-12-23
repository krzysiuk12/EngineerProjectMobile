package pl.edu.agh.fragments;

import android.os.Bundle;
import pl.edu.agh.activities.TripDayDetailsActivity;
import pl.edu.agh.activities.TripDetailsActivity;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Magda on 2014-12-22.
 */
public class TripDayListFragment extends AbstractListFragment<TripDay> {

	private static final String KEY_ITEM = "item";
	Trip trip;

	public static TripDayListFragment newInstance(Trip trip) {
		TripDayListFragment fragment = new TripDayListFragment();
		fragment.setInitialArguments(trip);
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
		return TripDayDetailsActivity.class;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(TripDay listItem, int index) {
		return TripDayDetailsFragment.newInstance(listItem, index);
	}

	@Override
	protected int getDetailsPaneId() {
		return R.id.ShowTrips_TripDetails;
	}

	protected void setInitialArguments(Trip listItem) {
		Bundle args = new Bundle();
		args.putSerializable(KEY_ITEM, listItem);
		setArguments(args);
	}

}

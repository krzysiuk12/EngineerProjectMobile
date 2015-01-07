package pl.edu.agh.activities.tripCreator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ListView;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.AbstractListFragment;
import pl.edu.agh.fragments.TripDayAdapter;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.util.ArrayList;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayListFragment extends AbstractListFragment<TripDay> {

	private Trip trip;
	private TripDay selectedTripDay;

	private BroadcastReceiver mUpdateUIReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			new AndroidLogService().debug("Received TripDayLocations");
			Object objects[] = (Object[]) intent.getExtras().get(TripCreatorDayLocationsFragment.RESULT_KEY);
			if ( selectedTripDay != null ) {
				selectedTripDay.getLocations().clear();
				for (Object object : objects) {
					TripDayLocation location = (TripDayLocation) object;
					selectedTripDay.getLocations().add(location);
				}
			}
		}
	};

	public static TripCreatorDayListFragment newInstance(Trip trip) {
		TripCreatorDayListFragment fragment = new TripCreatorDayListFragment();
		fragment.trip = trip;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				mUpdateUIReceiver,
				new IntentFilter("com.android.activity.SEND_DATA"));
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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		selectedTripDay = (TripDay) getAdapterInstance().getItem(position);
		super.onListItemClick(l, v, position, id);
	}

}

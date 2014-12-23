package pl.edu.agh.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.TripManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magda on 2014-12-22.
 */
public class TripDirectionsFragment extends ListFragment {

	protected static final String KEY_CURRENT_POSITION = "currentPosition";
	private static final String KEY_ITEM = "item";
	protected int currentPosition = 0;
	protected TripDay tripDay;
	private ITripManagementService tripManagementService = new TripManagementService(getActivity());

	public static TripDirectionsFragment newInstance(TripDay tripDay) {
		TripDirectionsFragment fragment = new TripDirectionsFragment();
		fragment.setInitialArguments(tripDay);
		fragment.tripDay = tripDay;
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(getAdapterInstance());

		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
		}
	}

	private ListAdapter getAdapterInstance() {
		ArrayList<TripDirection> tripDirections = new ArrayList<>();
		if ( tripDay.getTripSteps() != null ) {
			try {
				List<TripStep> tripSteps = tripManagementService.getTripSteps(tripDay);
				for (TripStep step : tripSteps) {
					try {
						tripDirections.addAll(tripManagementService.getTripDirections(step));
					} catch (TripException e) {
						e.printStackTrace();
					}
				}
			} catch (TripException e) {
				e.printStackTrace();
			}
		}
		return new TripDirectionAdapter(getActivity(), tripDirections);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CURRENT_POSITION, currentPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		getListView().setItemChecked(position, true);
	}

	protected void setInitialArguments(TripDay listItem) {
		Bundle args = new Bundle();
		args.putSerializable(KEY_ITEM, listItem);
		setArguments(args);
	}

}

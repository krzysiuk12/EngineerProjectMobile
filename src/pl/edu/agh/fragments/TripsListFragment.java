package pl.edu.agh.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import pl.edu.agh.activities.TripDetailsActivity;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.TripManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public class TripsListFragment extends AbstractListFragment<Trip> {

	private int detailsPaneId;
	private ITripManagementService tripManagementService;
	protected TripSelectionMode currentView;

	public TripsListFragment() {
		super();
		currentView = TripSelectionMode.ALL_TRIPS;
		detailsPaneId = R.id.ShowTrips_TripDetails;
		tripManagementService = new TripManagementService(getActivity());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	protected AbstractAdapter getAdapterInstance() {
		List<Trip> trips = new ArrayList<>();
		UserAccount currentUser = UserAccountManagementService.getUserAccount();

		try {
			switch ( currentView ) {
				case ALL_TRIPS :
					trips = tripManagementService.getAllTrips(currentUser);
					break;

				case PAST_TRIPS :
					trips = tripManagementService.getPastTrips(currentUser);
					break;

				case CURRENT_TRIPS :
					trips = tripManagementService.getCurrentTrips(currentUser);
					break;

				case FUTURE_TRIPS :
					trips = tripManagementService.getFutureTrips(currentUser);
					break;
			}
		} catch (TripException e) {
			e.printStackTrace();
		}

		return new TripAdapter(getActivity(), (ArrayList) trips);
	}

	@Override
	protected Class getClassForDetailsIntent() {
		return TripDetailsActivity.class;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragmentInstance(Trip listItem, int index) {
		return TripDetailsFragment.newInstance(listItem, index);
	}

	@Override
	protected int getDetailsPaneId() {
		return detailsPaneId;
	}

	// <editor-fold desc="ActionBar Menu">

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.show_trips_menu, menu);
		setHasOptionsMenu(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch ( item.getItemId() ) {
			case R.id.ShowTripsMenu_TripViewMode_All:
				currentView = TripSelectionMode.ALL_TRIPS;
				break;

			case R.id.ShowTripsMenu_TripViewMode_Past:
				currentView = TripSelectionMode.PAST_TRIPS;
				break;

			case R.id.ShowTripsMenu_TripViewMode_Current:
				currentView = TripSelectionMode.CURRENT_TRIPS;
				break;

			case R.id.ShowTripsMenu_TripViewMode_Future:
				currentView = TripSelectionMode.FUTURE_TRIPS;
				break;
		}
		setListAdapter(getAdapterInstance());
		return true;
	}

	// </editor-fold>
}

package pl.edu.agh.fragments;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.activities.TripDetailsActivity;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.TripManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public class TripsListFragment extends AbstractListFragment<Trip> {

	enum TripView {
		ALL_TRIPS,
		FUTURE_TRIPS,
		PAST_TRIPS,
		CURRENT_TRIPS
	}

	private int detailsPaneId;
	protected TripView currentView;

	public TripsListFragment() {
		super();
		currentView = TripView.ALL_TRIPS;
		detailsPaneId = R.id.ShowTrips_TripDetails;
	}

	@Override
	protected AbstractAdapter getAdapterInstance() {
		List<Trip> trips = new ArrayList<>();

		try {
			trips = new TripManagementService().getAllTrips();
		} catch (TripException e) {
			e.printStackTrace();
		}

//		((OrmLiteBaseActivity) getActivity()).getHelper()

		//todo : download data
		switch ( currentView ) {
			case ALL_TRIPS :
				break;

			case PAST_TRIPS :
				break;

			case CURRENT_TRIPS :
				break;

			case FUTURE_TRIPS :
				break;
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

}

package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayLocationsFragment extends AbstractDescriptionFragment<TripDay> {

    IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
    private TripDay tripDay;
    private MapFragment mapFragment;
    private GoogleMap googleMap;

	public static TripCreatorDayLocationsFragment newInstance(TripDay tripDay, long index) {
		TripCreatorDayLocationsFragment fragment = new TripCreatorDayLocationsFragment();
		fragment.setInitialArguments(index, tripDay);
		return fragment;
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.TripDayDetails_Map);
        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.TripDayDetails_Map, mapFragment, "map");
            fragmentTransaction.commit();
        }


    }

	@Override
	protected int getLayoutId() {
		return 0;
	}

	@Override
	protected void showDetails() {

	}
}

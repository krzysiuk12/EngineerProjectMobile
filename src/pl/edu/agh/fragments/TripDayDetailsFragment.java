package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

/**
 * Created by Magda on 2014-12-12.
 */
public class TripDayDetailsFragment extends AbstractDescriptionFragment<Trip> {

	IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
	private GoogleMap googleMap;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.trip_details_tripday, container, false);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.trip_details_tripday;
	}

	@Override
	protected void showDetails() {

	}


	public GoogleMap getGoogleMap() {
		if ( googleMap == null ) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.TripDayDetail_Map)).getMap();
		}
		return googleMap;
	}

	private void drawTrip() {

	}
}

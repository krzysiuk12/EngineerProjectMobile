package pl.edu.agh.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magda on 2014-12-22.
 */
public class TripDayDetailsFragment extends AbstractDescriptionFragment<TripDay> {

	IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
	private MapFragment mapFragment;
	private GoogleMap googleMap;
	private TripDay tripDay;

	public static TripDayDetailsFragment newInstance(TripDay tripDay, long index) {
		TripDayDetailsFragment fragment = new TripDayDetailsFragment();
		fragment.setInitialArguments(index, tripDay);
		return fragment;
	}

//	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager fm = getFragmentManager();
		mapFragment = (MapFragment) fm.findFragmentById(R.id.TripDayDetails_Map);
		if (mapFragment == null) {
			mapFragment = MapFragment.newInstance();
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.add(R.id.TripDayDetails_Map, mapFragment, "map");
			fragmentTransaction.commit();
		}
		MapsInitializer.initialize(getActivity());
		getGoogleMap();
		tripDay = (TripDay) getArguments().getSerializable(KEY_ITEM);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		((Button) view.findViewById(R.id.TripDayDetails_TripDirectionsButton)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTripDirections(v);
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		googleMapsManagementService.setMyLocationEnabled(getGoogleMap());
		drawTrip();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.tripday_details_fragment;
	}

	@Override
	protected void showDetails() {
		tripDay = (TripDay) getArguments().getSerializable(KEY_ITEM);
	}

	public GoogleMap getGoogleMap() {
		if ( googleMap == null ) {
			googleMap = mapFragment.getMap();
		}
		return googleMap;
	}

	private void drawTrip() {
		if ( tripDay.getLocations() != null ) {
			for ( TripDayLocation location : tripDay.getLocations() ) {
				drawLocation(location.getLocation());
			}
			setMapPosition();
		}
	}

	private void drawLocation(Location location) {
		MarkerOptions markerOptions = googleMapsManagementService.createLocationMarker(location);
		getGoogleMap().addMarker(markerOptions);
	}

	private void setMapPosition() {
		List<TripDayLocation> tripDayLocations = new ArrayList<>(tripDay.getLocations());
		if ( !tripDayLocations.isEmpty() ) {
			Location location = tripDayLocations.get(0).getLocation();
			LatLng postition = new LatLng(location.getLatitude(), location.getLongitude());
			googleMapsManagementService.setMapPositionWithZoom(getGoogleMap(), postition, 10);
		}
	}

	private void showTripDirections(View view) {
		TripDirectionsFragment details = TripDirectionsFragment.newInstance((TripDay) getArguments().getSerializable(KEY_ITEM));
		getFragmentManager().beginTransaction().replace(android.R.id.content, details).commit();
	}

}

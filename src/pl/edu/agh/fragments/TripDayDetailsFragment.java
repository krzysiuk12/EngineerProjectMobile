package pl.edu.agh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

import java.util.Collection;

/**
 * Created by Magda on 2014-12-12.
 */
public class TripDayDetailsFragment extends AbstractDescriptionFragment<Trip> {

	IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
	private GoogleMap googleMap;
	private Trip trip;

	public static TripDayDetailsFragment newInstance(Trip trip, long index) {
		TripDayDetailsFragment fragment = new TripDayDetailsFragment();
		fragment.setInitialArguments(index, trip);
		return fragment;
	}

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
		trip = (Trip) getArguments().getSerializable(KEY_ITEM);
		drawTrip();
	}


	public GoogleMap getGoogleMap() {
		if ( googleMap == null ) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.TripDayDetail_Map)).getMap();
		}
		return googleMap;
	}

	private void drawTrip() {
//		getLogService().debug(this.getClass().getName(), "draw trips");
		Collection<TripDay> tripDays = trip.getDays();
		if ( tripDays != null ) {
			new AndroidLogService().debug("tripdays not null:" + tripDays.size());
			new AndroidLogService().debug(tripDays.toString());
			for ( TripDay tripDay : tripDays ) {
				if ( tripDay.getLocations() != null ) {
					new AndroidLogService().debug("trip locations");

					for ( TripDayLocation location : tripDay.getLocations() ) {
//						drawLocation(location.getLocation());
					}
				}
				if ( tripDay.getTripSteps() != null ) {
					new AndroidLogService().debug("tripsteeeps");
				}
			}
		}
	}

	private void drawLocation(Location location) {
		MarkerOptions markerOptions = googleMapsManagementService.createLocationMarker(location);
		getGoogleMap().addMarker(markerOptions);
	}

}

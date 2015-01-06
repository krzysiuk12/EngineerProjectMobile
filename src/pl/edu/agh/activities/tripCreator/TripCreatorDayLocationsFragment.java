package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayLocationsFragment extends AbstractDescriptionFragment<TripDay> {

    IGoogleMapsManagementService googleMapsManagementService;
    ILocationManagementService locationManagementService;
    private TripDay tripDay;
    private List<TripDayLocation> locationList = new ArrayList<>();
    private Map<Marker, Location> markerLocationMap = new HashMap<>();
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
        tripDay = (TripDay) getArguments().getSerializable(KEY_ITEM);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.TripCreator_TripDayLocation_Map);
        if ( mapFragment == null ) {
            mapFragment = MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.TripCreator_TripDayLocation_Map, mapFragment, "map");
            fragmentTransaction.commit();
        }
        MapsInitializer.initialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ((Button) view.findViewById(R.id.TripCreator_TripDayLocation_Button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripDay.setLocations(locationList);
                new InfoToastBuilder(getActivity(), StringUtils.getString(getActivity(), R.string.TripCreator_TripDayLocation_LocationsSet)).build().show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getGoogleMapsManagementService().setMyLocationEnabled(getGoogleMap());

        getGoogleMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Location location = markerLocationMap.get(marker);

                if ( location != null ) {
                    TripDayLocation tripDayLocation = new TripDayLocation();
                    tripDayLocation.setLocation(location);
                    locationList.add(tripDayLocation);
                    // TODO: order!
                }

                return true;
            }
        });

        displayLocationsOnMap();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.trip_creator_locations;
    }

    @Override
    protected void showDetails() {

    }

    // <editor-fold desc="Getters">

    private GoogleMap getGoogleMap() {
        if ( googleMap == null ) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.TripCreator_TripDayLocation_Map)).getMap();
        }
        return googleMap;
    }

    private IGoogleMapsManagementService getGoogleMapsManagementService() {
        if ( googleMapsManagementService == null ) {
            googleMapsManagementService = new GoogleMapsManagementService();
        }
        return googleMapsManagementService;
    }

    private ILocationManagementService getLocationManagementService() {
        if ( locationManagementService == null ) {
            locationManagementService = new LocationManagementService(getActivity());
        }
        return locationManagementService;
    }

    // </editor-fold>

    private void displayLocationsOnMap() {
        try {
            List<Location> availableLocations = getLocationManagementService().getAllLocations(UserAccountManagementService.getUserAccount());

            if ( availableLocations != null ) {
                for ( Location location : availableLocations ) {
                    MarkerOptions markerOptions = getGoogleMapsManagementService().createLocationMarker(location);

                    Marker marker = getGoogleMap().addMarker(markerOptions);
                    markerLocationMap.put(marker, location);
                }
            }
        } catch (LocationException e) {
            e.printStackTrace();
        }
    }

}

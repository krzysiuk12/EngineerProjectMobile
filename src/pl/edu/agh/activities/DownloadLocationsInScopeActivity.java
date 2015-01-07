package pl.edu.agh.activities;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.layout.listeners.AfterTextChangedTextWatcher;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.implementation.SynchronizationService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;
import pl.edu.agh.services.interfaces.ISynchronizationService;
import pl.edu.agh.tools.ErrorTools;

import java.io.Serializable;

/**
 * Created by Magda on 2014-12-26.
 */
public class DownloadLocationsInScopeActivity extends ActivityWithMapMenu {

	public enum MetricUnitType implements Serializable {
		KM,
		MILES
	}

	private ISynchronizationService synchronizationService;
	private IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
	private GoogleMap googleMap;
	private Marker marker;
	private Circle circle;
	private double radius = 50;
	private Spinner unitSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_locations_in_scope_fragment);

		synchronizationService = new SynchronizationService(this);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				((EditText) findViewById(R.id.Synchronization_DownloadLocationsInScope_RadiusEditText)).addTextChangedListener(new AfterTextChangedTextWatcher() {
					@Override
					public void afterTextChanged(Editable s) {
						try {
							radius = Double.parseDouble(s.toString());
						} catch (NumberFormatException e) {
							radius = 0;
						}
						renderScope();
					}
				});

				getGoogleMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
					@Override
					public void onMapClick(LatLng latLng) {
						onMapClickAction(latLng);
					}
				});

				unitSpinner = (Spinner) findViewById(R.id.Synchronization_DownloadLocationsInScope_UnitSpinner);
				ArrayAdapter<MetricUnitType> adapter = new ArrayAdapter<MetricUnitType>(DownloadLocationsInScopeActivity.this, android.R.layout.simple_spinner_dropdown_item, MetricUnitType.values());
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				unitSpinner.setAdapter(adapter);
				unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						renderScope();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});

			}
		});
	}

	// <editor-fold desc="Getters">

	public GoogleMap getGoogleMap() {
		if ( googleMap == null ) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.Synchronization_DownloadLocationsInScope_Map)).getMap();
		}
		return googleMap;
	}

	public IGoogleMapsManagementService getGoogleMapsManagementService() {
		if ( googleMapsManagementService == null ) {
			googleMapsManagementService = new GoogleMapsManagementService();
		}
		return googleMapsManagementService;
	}

	// </editor-fold>


	protected void helpMenuAction() {
		// TODO: redirect to help
	}

	public void onDownloadLocationsButtonClicked(View view) {
		if ( marker == null ) {
			new ErrorToastBuilder(this, getString(R.string.Synchronization_DownloadLocations_LocationIsRequired)).build().show();
			return;
		}
		LatLng position = marker.getPosition();
		try {
			synchronizationService.downloadLocationsInScope(position.latitude, position.longitude, calculateRadiusInKilometers());
			new InfoToastBuilder(this, getString(R.string.Synchronization_DownloadLocations_Success)).build().show();
		} catch (SynchronizationException e) {
			new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e)).build().show();
			e.printStackTrace();
		}
	}

	// <editor-fold desc="Drawing on map">

	private void onMapClickAction(LatLng latLng) {
		if ( marker == null ) {
			MarkerOptions markerOptions = getGoogleMapsManagementService().createNewMarker(latLng);
			marker = getGoogleMap().addMarker(markerOptions);
		} else {
			marker.setPosition(latLng);
		}
		renderScope();
	}

	private void renderScope() {
		if ( marker != null && radius > 0 ) {
			renderCircle();
		}
	}

	private void renderCircle() {
		if ( circle == null ) {
			CircleOptions circleOptions = new CircleOptions();
			circleOptions.center(marker.getPosition());
			circleOptions.radius(calculateRadiusInMeters());
			circleOptions.strokeWidth(3);
			circle = getGoogleMap().addCircle(circleOptions);
		} else {
			circle.setCenter(marker.getPosition());
			circle.setRadius(calculateRadiusInMeters());
		}
	}

	// </editor-fold>

	// <editor-fold desc="Radius calculations">

	private double calculateRadiusInMeters() {
		if ( unitSpinner.getSelectedItem() == MetricUnitType.MILES )
			return radius * 1609.3;
		else {
			return radius * 1000;
		}
	}

	private double calculateRadiusInKilometers() {
		if ( unitSpinner.getSelectedItem() == MetricUnitType.MILES )
			return radius * 1.6093;
		else
			return radius;
	}

	// </editor-fold>
}

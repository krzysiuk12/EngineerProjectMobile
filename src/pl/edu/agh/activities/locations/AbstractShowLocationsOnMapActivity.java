package pl.edu.agh.activities.locations;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.activities.SynchronizationActivity;
import pl.edu.agh.activities.help.BaseHelpElement;
import pl.edu.agh.activities.help.HelpElementDescriptionActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.layout.GeocodeSearchDialogFragment;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.GoogleGeocodeService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IGoogleGeocodeService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.tools.ErrorTools;
import pl.edu.agh.tools.RenderingTools;

import java.util.List;

/**
 * Created by Magda on 2014-12-10.
 */
public abstract class AbstractShowLocationsOnMapActivity extends OrmLiteBaseActivity<TestDatabaseHelper> implements GeocodeSearchDialogFragment.GeocodeSearchDialogListener {

	//<editor-fold desc="Fields">
	private IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
	private IGoogleGeocodeService googleGeocodeService = new GoogleGeocodeService();
	private ILocationManagementService locationManagementService = new LocationManagementService(this);
	private GoogleMap googleMap;
	private Marker selectedMarker = null;
	//</editor-fold>

	//<editor-fold desc="Lifecycle Methods - onCreate">
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_all_locations_on_map_activity);

		displayMarkersOnMap();

		getGoogleMapsManagementService().setMyLocationEnabled(getGoogleMap());
		getGoogleMapsManagementService().setMapPosition(getGoogleMap(), getGoogleMapsManagementService().getMyLocation(getGoogleMap()), 15);

		getShowDescriptionButton().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDescriptionAction(view);
			}
		});
		getDownloadLocationsButton().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				downloadLocationsAction(view);
			}
		});

		getGoogleMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				getDescriptionNameTextView().setText(marker.getTitle());
				getDescriptionStatusTextView().setText(marker.getSnippet());
				setSelectedMarker(marker);
				return true;
			}
		});

	}
	//</editor-fold>


	//<editor-fold desc="ActionBar Menu - Methods">
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_all_locations_on_map_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
			case R.id.ShowAllLocationsOnMapMenu_Search_Online:
				searchOnlineMenuAction();
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Search_InMemory:
				searchInMemoryMenuAction();
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Help:
				helpMenuAction();
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Settings_GoogleMapsType_Hybrid:
				getGoogleMapsManagementService().setHybridMapType(getGoogleMap());
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Settings_GoogleMapsType_Normal:
				getGoogleMapsManagementService().setNormalMapType(getGoogleMap());
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Settings_GoogleMapsType_Satellite:
				getGoogleMapsManagementService().setSatelliteMapType(getGoogleMap());
				return true;
			case R.id.ShowAllLocationsOnMapMenu_Settings_GoogleMapsType_Terrain:
				getGoogleMapsManagementService().setTerrainMapType(getGoogleMap());
				return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	//</editor-fold>


	//<editor-fold desc="Actions">
	private void searchOnlineMenuAction() {
		new GeocodeSearchDialogFragment().show(getFragmentManager(), "GeocodeFragment");
	}

	private void searchInMemoryMenuAction() {
		new GeocodeSearchDialogFragment().show(getFragmentManager(), "GeocodeFragment");
	}

	private void helpMenuAction() {
		Intent intent = new Intent();
		intent.setClass(this, HelpElementDescriptionActivity.class);
		intent.putExtra(AbstractDescriptionFragment.KEY_INDEX, 0);
		intent.putExtra(AbstractDescriptionFragment.KEY_ITEM, BaseHelpElement.SHOW_LOCATIONS_ON_MAP.getHelpElement(getResources()));
		startActivity(intent);
	}

	private void showDescriptionAction(View view) {
		if ( selectedMarker == null ) {
			new ErrorToastBuilder(this, getString(R.string.ShowAllLocationsOnMapActivity_LocationNullError)).build().show();
			return;
		}

		Location location = null;
		try {
			LatLng position = selectedMarker.getPosition();
			location = getLocationManagementService().getLocationByCoordinates(position.latitude, position.longitude);
		} catch (LocationException e) {
			new ErrorToastBuilder(this, getString(R.string.ShowAllLocationsOnMapActivity_NoLocationFoundError)).build().show();
			e.printStackTrace();
		}

		showDetailsForLocation(location);
	}

	private void showDetailsForLocation(Location location) {
		if ( location != null ) {
			Intent intent = new Intent();
			intent.setClass(this, LocationDescriptionActivity.class);
			intent.putExtra(AbstractDescriptionFragment.KEY_INDEX, 0);  // TODO: proper ID ?
			intent.putExtra(AbstractDescriptionFragment.KEY_ITEM, location);
			startActivity(intent);
		}
	}

	private void downloadLocationsAction(View view) {
		Intent intent = new Intent();
		intent.setClass(this, SynchronizationActivity.class);
		startActivity(intent);
	}
	//</editor-fold>


	//<editor-fold desc="Getters and Setters">
	public IGoogleGeocodeService getGoogleGeocodeService() {
		return googleGeocodeService;
	}

	public void setGoogleGeocodeService(IGoogleGeocodeService geocodingService) {
		this.googleGeocodeService = geocodingService;
	}

	public IGoogleMapsManagementService getGoogleMapsManagementService() {
		return googleMapsManagementService;
	}

	public void setGoogleMapsManagementService(IGoogleMapsManagementService googleMapsManagementService) {
		this.googleMapsManagementService = googleMapsManagementService;
	}

	public ILocationManagementService getLocationManagementService() {
		return locationManagementService;
	}

	public void setLocationManagementService(ILocationManagementService locationManagementService) {
		this.locationManagementService = locationManagementService;
	}

	public GoogleMap getGoogleMap() {
		if(googleMap == null) {
			googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.ShowAllLocationsOnMapActivity_Map)).getMap();
		}
		return googleMap;
	}

	public Marker getSelectedMarker() {
		return selectedMarker;
	}

	public void setSelectedMarker(Marker selectedMarker) {
		this.selectedMarker = selectedMarker;
	}

	//</editor-fold>


	//<editor-fold desc="Getters and Setters - Layout Elements">
	private TextView getDescriptionNameTextView() {
		return (TextView)findViewById(R.id.ShowAllLocationsOnMapActivity_Description_Name);
	}

	private TextView getDescriptionStatusTextView() {
		return (TextView)findViewById(R.id.ShowAllLocationsOnMapActivity_Description_Status);
	}

	private Button getShowDescriptionButton() {
		return (Button) findViewById(R.id.ShowAllLocationsOnMapActivity_ShowDescriptionButton);
	}

	private Button getDownloadLocationsButton() {
		return (Button) findViewById(R.id.ShowAllLocationsOnMapActivity_DownloadLocationsButton);
	}
	//</editor-fold>

	//<editor-fold desc="GeocodeSearchDialogListener Implementation">
	@Override
	public void onDialogPositiveClick(String locationInfo) {
		try {
			Location location = getGoogleGeocodeService().getLocationByAddress(UserAccountManagementService.getToken(), locationInfo, null, null);
			updateView(location);
		} catch(GoogleGeocodingException ex) {
			ex.printStackTrace();
			new ErrorToastBuilder(this, getString(R.string.ShowAllLocationsOnMapActivity_SearchError)).build().show();
		} catch (SynchronizationException e) {
			e.printStackTrace();
			new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e)).build().show();
		}
	}

	private void updateView(Location location) {
		getGoogleMapsManagementService().setMapPosition(getGoogleMap(), getGoogleMapsManagementService().getLatLngFromLocation(location), 15);
		if ( location.getName() != null ) {
			getDescriptionNameTextView().setText(location.getName());
		}
		if ( location.getStatus() != null ) {
			getDescriptionStatusTextView().setText(location.getStatus().toString());
		}
	}
	//</editor-fold>

	protected void displayMarkersOnMap() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					List<Location> locations = getLocations();
					if ( locations != null ) {

						for ( Location location : locations ) {
							MarkerOptions markerOptions = getGoogleMapsManagementService().createLocationMarker(location);
							getGoogleMap().addMarker(markerOptions);
						}
					}
				} catch (LocationException e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected abstract List<Location> getLocations() throws LocationException;

}

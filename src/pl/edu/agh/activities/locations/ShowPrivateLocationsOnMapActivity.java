package pl.edu.agh.activities.locations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import pl.edu.agh.activities.LocationDescriptionActivity;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.layout.GeocodeSearchDialogFragment;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.services.implementation.GoogleGeocodingService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleGeocodingService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

/**
 * Created by Krzysztof Kicinger on 2014-10-12.
 */
public class ShowPrivateLocationsOnMapActivity extends Activity implements GeocodeSearchDialogFragment.GeocodeSearchDialogListener {

    //<editor-fold desc="Fields">
    private IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
    private IGoogleGeocodingService googleGeocodingService = new GoogleGeocodingService();
    private GoogleMap googleMap;
    //</editor-fold>

    //<editor-fold desc="Lifecycle Methods - onCreate">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_private_locations_on_map_activity);

        //TODO: Load from db private locations and create markers

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
                return true;
            }
        });

    }
    //</editor-fold>

    //<editor-fold desc="ActionBar Menu - Methods">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_private_locations_on_map_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case R.id.ShowPrivateLocationsOnMapMenu_Search_Online:
                searchOnlineMenuAction();
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Search_InMemory:
                searchInMemoryMenuAction();
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Help:
                helpMenuAction();
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Settings_GoogleMapsType_Hybrid:
                getGoogleMapsManagementService().setHybridMapType(getGoogleMap());
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Settings_GoogleMapsType_Normal:
                getGoogleMapsManagementService().setNormalMapType(getGoogleMap());
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Settings_GoogleMapsType_Satellite:
                getGoogleMapsManagementService().setSatelliteMapType(getGoogleMap());
                return true;
            case R.id.ShowPrivateLocationsOnMapMenu_Settings_GoogleMapsType_Terrain:
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
        new InfoToastBuilder(this, "Wybrano help").build().show();
    }

    private void showDescriptionAction(View view) {
        Intent intent = new Intent();
        intent.setClass(this, LocationDescriptionActivity.class);
        startActivity(intent);
    }

    private void downloadLocationsAction(View view) {
        Toast.makeText(getApplicationContext(), "download locations", Toast.LENGTH_SHORT).show();
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public IGoogleGeocodingService getGoogleGeocodingService() {
        return googleGeocodingService;
    }

    public void setGoogleGeocodingService(IGoogleGeocodingService geocodingService) {
        this.googleGeocodingService = geocodingService;
    }

    public IGoogleMapsManagementService getGoogleMapsManagementService() {
        return googleMapsManagementService;
    }

    public void setGoogleMapsManagementService(IGoogleMapsManagementService googleMapsManagementService) {
        this.googleMapsManagementService = googleMapsManagementService;
    }

    public GoogleMap getGoogleMap() {
        if(googleMap == null) {
            googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.ShowPrivateLocationsOnMapActivity_Map)).getMap();
        }
        return googleMap;
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters - Layout Elements">
    private TextView getDescriptionNameTextView() {
        return (TextView)findViewById(R.id.ShowPrivateLocationsOnMapActivity_Description_Name);
    }

    private TextView getDescriptionStatusTextView() {
        return (TextView)findViewById(R.id.ShowPrivateLocationsOnMapActivity_Description_Status);
    }

    private Button getShowDescriptionButton() {
        return (Button) findViewById(R.id.ShowPrivateLocationsOnMapActivity_ShowDescriptionButton);
    }

    private Button getDownloadLocationsButton() {
        return (Button) findViewById(R.id.ShowPrivateLocationsOnMapActivity_DownloadLocationsButton);
    }
    //</editor-fold>

    //<editor-fold desc="GeocodeSearchDialogListener Implementation">
    @Override
    public void onDialogPositiveClick(String locationName) {
        try {
            GoogleGeocodingSerializer serializer = getGoogleGeocodingService().getLocationDescription(locationName, null, null);
            Location location = getGoogleGeocodingService().deserializeLocationForLatLng(serializer);
            getGoogleMapsManagementService().setMapPosition(getGoogleMap(), getGoogleMapsManagementService().getLatLngFromLocation(location), 15);
        } catch(GoogleGeocodingException ex) {
            //TODO: error dialog, toast
        }
    }
    //</editor-fold>

}
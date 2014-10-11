package pl.edu.agh.activities.locations;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.layout.GeocodeSearchDialogFragment;
import pl.edu.agh.layout.listeners.AfterTextChangedTextWatcher;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.services.implementation.GoogleGeocodingService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.interfaces.IGoogleGeocodingService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;
import pl.edu.agh.utils.StringUtils;

/**
 * Created by Sławomir on 19.06.14.
 * Edited by Krzysiu on 16.09.14
 */
public class AddLocationActivity extends Activity implements GeocodeSearchDialogFragment.GeocodeSearchDialogListener {

    private IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
    private IGoogleGeocodingService geocodingService = new GoogleGeocodingService();
    private GoogleMap googleMap;
    private EditText locationNameEditText;
    private EditText locationDescriptionEditText;
    private EditText locationAddressStreetEditText;
    private EditText locationAddressCityEditText;
    private EditText locationAddressPostalCodeEditText;
    private EditText locationAddressCountryEditText;
    private Location location;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_activity);

        getLocationNameEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().setName(getLocationNameEditText().getText().toString());
            }
        });

        getLocationDescriptionEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().setDescription(getLocationDescriptionEditText().getText().toString());
            }
        });

        getLocationAddressStreetEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().getAddress().setStreet(getLocationAddressStreetEditText().getText().toString());
            }
        });

        getLocationAddressCityEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().getAddress().setCity(getLocationAddressCityEditText().getText().toString());
            }
        });

        getLocationAddressPostalCodeEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().getAddress().setPostalCode(getLocationAddressPostalCodeEditText().getText().toString());
            }
        });

        getLocationAddressCountryEditText().addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getLocation().getAddress().setCountry(getLocationAddressCountryEditText().getText().toString());
            }
        });


        getUsersPrivateCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                getLocation().setUsersPrivate(!isChecked);
            }
        });


        getAddLocationButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocationAction();
            }
        });
/*        LatLng myLocation = getGoogleMapsManagementService().getMyLocation(getGoogleMap());
        if(myLocation != null) {
            getGoogleMapsManagementService().setMapPosition(getGoogleMap(), myLocation, 15);
        }*/

    }

    //<editor-fold desc="ActionBar Menu - Creation and Actions">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_location_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.AddLocationMenu_Search:
                searchMenuAction();
                return true;
            case R.id.AddLocationMenu_Help:
                helpMenuAction();
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Hybrid:
                googleMapsManagementService.setHybridMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Normal:
                googleMapsManagementService.setNormalMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Satellite:
                googleMapsManagementService.setSatelliteMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Terrain:
                googleMapsManagementService.setTerrainMapType(getGoogleMap());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters - Layout Elements">
    public EditText getLocationNameEditText() {
        if(locationNameEditText == null) {
            locationNameEditText = ((EditText)findViewById(R.id.AddLocation_LocationNameEditText));
        }
        return locationNameEditText;
    }

    public EditText getLocationDescriptionEditText() {
        if(locationDescriptionEditText == null) {
            locationDescriptionEditText = ((EditText)findViewById(R.id.AddLocation_LocationDescriptionEditText));
        }
        return locationDescriptionEditText;
    }

    public EditText getLocationAddressStreetEditText() {
        if(locationAddressStreetEditText == null) {
            locationAddressStreetEditText = ((EditText)findViewById(R.id.AddLocation_Address_Street_EditText));
        }
        return locationAddressStreetEditText;
    }

    public EditText getLocationAddressCityEditText() {
        if(locationAddressCityEditText == null) {
            locationAddressCityEditText = ((EditText)findViewById(R.id.AddLocation_Address_City_EditText));
        }
        return locationAddressCityEditText;
    }

    public EditText getLocationAddressPostalCodeEditText() {
        if(locationAddressPostalCodeEditText == null) {
            locationAddressPostalCodeEditText = ((EditText)findViewById(R.id.AddLocation_Address_PostalCode_EditText));
        }
        return locationAddressPostalCodeEditText;
    }

    public EditText getLocationAddressCountryEditText() {
        if(locationAddressCountryEditText == null) {
            locationAddressCountryEditText = ((EditText)findViewById(R.id.AddLocation_Address_Country_EditText));
        }
        return locationAddressCountryEditText;
    }

    public CheckBox getUsersPrivateCheckBox() {
        return ((CheckBox)findViewById(R.id.AddLocation_UsersPrivateCheckbox));
    }

    public Button getAddLocationButton() {
        return ((Button)findViewById(R.id.AddLocation_Button));
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public IGoogleGeocodingService getGeocodingService() {
        return geocodingService;
    }

    public void setGeocodingService(IGoogleGeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    public IGoogleMapsManagementService getGoogleMapsManagementService() {
        return googleMapsManagementService;
    }

    public void setGoogleMapsManagementService(IGoogleMapsManagementService googleMapsManagementService) {
        this.googleMapsManagementService = googleMapsManagementService;
    }

    public GoogleMap getGoogleMap() {
        if(googleMap == null) {
            googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.AddLocation_map)).getMap();
        }
        return googleMap;
    }

    public Location getLocation() {
        if(location == null) {
            location = new Location();
            location.setAddress(new Address());
        }
        return location;
    }
    //</editor-fold>

    //<editor-fold desc="Actions">
    public void searchMenuAction() {
        new GeocodeSearchDialogFragment().show(getFragmentManager(), "GeocodeFragment");
        /*try {
            GoogleGeocodingSerializer serializer = getGeocodingService().getLocationDescription("agh", null, null);
        } catch(GoogleGeocodingException ex) {
            //TODO: error dialog
        }*/
    }

    public void helpMenuAction() {
        new InfoToastBuilder(this, "Wybrano help").build().show();
    }

    public void addLocationAction() {
        //TODO: Save location - catch exception (if happened with FormValidationErrors then errorToastBuilder and the same page) if ok InfoToastBuilder with ok message
        new InfoToastBuilder(this, StringUtils.getString(this, R.string.AddLocation_NewLocationAdded)).build().show();
        finish();
    }
    //</editor-fold>

    //<editor-fold desc="GeocodeSearchDialogListener Implementation">
    @Override
    public void onDialogPositiveClick(String locationName) {
        try {
            GoogleGeocodingSerializer serializer = getGeocodingService().getLocationDescription(locationName, null, null);
            Location location = getGeocodingService().deserializeLocationDescription(serializer);
            updateFormFields(location);
        } catch(GoogleGeocodingException ex) {
            //TODO: error dialog, toast
        }
    }
    //</editor-fold>

    public void updateFormFields(Location location) {
        getLocationNameEditText().setText(location.getName());
        getLocationDescriptionEditText().setText(location.getDescription());
        getLocationAddressCityEditText().setText(location.getAddress().getCity());
        getLocationAddressCountryEditText().setText(location.getAddress().getCountry());
        getLocationAddressPostalCodeEditText().setText(location.getAddress().getPostalCode());
        getLocationAddressStreetEditText().setText(location.getAddress().getStreet());
    }

}
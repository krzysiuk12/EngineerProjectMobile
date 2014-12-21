package pl.edu.agh.activities.locations;

import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.layout.GeocodeSearchDialogFragment;
import pl.edu.agh.layout.listeners.AfterTextChangedTextWatcher;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.implementation.GoogleGeocodingService;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IGoogleGeocodingService;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.tools.ErrorTools;
import pl.edu.agh.utils.StringUtils;
import pl.edu.agh.views.ScrollViewWithMap;

import java.util.Date;

/**
 * Created by Sławomir on 19.06.14.
 * Edited by Krzysiu on 16.09.14
 */
public class AddLocationActivity extends OrmLiteBaseActivity<TestDatabaseHelper> implements GeocodeSearchDialogFragment.GeocodeSearchDialogListener {

    //<editor-fold desc="Fields">
    private IGoogleMapsManagementService googleMapsManagementService = new GoogleMapsManagementService();
    private IGoogleGeocodingService geocodingService = new GoogleGeocodingService();
    private ILocationManagementService locationManagementService;
    private GoogleMap googleMap;
    private EditText locationNameEditText;
    private EditText locationDescriptionEditText;
    private EditText locationAddressStreetEditText;
    private EditText locationAddressCityEditText;
    private EditText locationAddressPostalCodeEditText;
    private EditText locationAddressCountryEditText;
	private Spinner locationStatusSpinner;
    private Location location;
    private Marker newLocationMarker;
	//</editor-fold>

    //<editor-fold desc="Lifecycle Methods - onCreate">
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManagementService = new LocationManagementService(this);
        setContentView(R.layout.add_location_activity);

        ((ScrollViewWithMap) findViewById(R.id.AddLocation_ScrollView)).addInterceptScrollView(findViewById(R.id.AddLocation_map));

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

	    getLocationStatusSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			    getLocation().setStatus((Location.Status) parent.getItemAtPosition(position));
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
                getLocation().setStatus(Location.Status.DRAFT); // default
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
		        AndroidLogService service = new AndroidLogService();
		        service.error("is Checked fired");
		        getLocation().setUsersPrivate(!isChecked);
	        }
        });


        getAddLocationButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocationAction();
            }
        });

        getGoogleMapsManagementService().setMyLocationEnabled(getGoogleMap());
        getGoogleMapsManagementService().setMapPosition(getGoogleMap(), getGoogleMapsManagementService().getMyLocation(getGoogleMap()), 15);

        getGoogleMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if ( newLocationMarker == null ) {
                    MarkerOptions markerOptions = getGoogleMapsManagementService().createNewMarker(latLng);
                    newLocationMarker = getGoogleMap().addMarker(markerOptions);
                } else {
                    newLocationMarker.setPosition(latLng);
                }
                getLocation().setLatitude(latLng.latitude);
                getLocation().setLongitude(latLng.longitude);
            }
        });
    }
    //</editor-fold>

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
                getGoogleMapsManagementService().setHybridMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Normal:
                getGoogleMapsManagementService().setNormalMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Satellite:
                getGoogleMapsManagementService().setSatelliteMapType(getGoogleMap());
                return true;
            case R.id.AddLocationMenu_Settings_GoogleMapsType_Terrain:
                getGoogleMapsManagementService().setTerrainMapType(getGoogleMap());
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

	public Spinner getLocationStatusSpinner() {
		if ( locationStatusSpinner == null ) {
			locationStatusSpinner = ((Spinner) findViewById(R.id.AddLocation_StatusSpinner));
		}
		return locationStatusSpinner;
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

    public ILocationManagementService getLocationManagementService() {
        return locationManagementService;
    }

    public void setLocationManagementService(ILocationManagementService locationManagementService) {
        this.locationManagementService = locationManagementService;
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
            location.setUsersPrivate(true);
        }
        return location;
    }
    //</editor-fold>

    //<editor-fold desc="Actions">
    private void searchMenuAction() {
        new GeocodeSearchDialogFragment().show(getFragmentManager(), "GeocodeFragment");
    }

    private void helpMenuAction() {
        new InfoToastBuilder(this, "Wybrano help").build().show();
    }

    public void addLocationAction() {
        if ( newLocationMarker == null ) {
            new ErrorToastBuilder(this, getString(R.string.LocationException_ValidationError_LocationOnMapIsRequired)).build().show();  // todo: do sth with stack so the back button will work correctly
        } else {
            try {
                getLocationManagementService().saveNewLocation(location);

                new InfoToastBuilder(this, StringUtils.getString(this, R.string.AddLocation_NewLocationAdded)).build().show();
                finish();
            } catch (LocationException e) {
                String message = ErrorTools.createFormValidationErrorString(this.getResources(), e.getFormValidationErrors());
                new ErrorToastBuilder(this, message).build().show();
                e.printStackTrace();
            }
        }

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
	        CharSequence error = "Error downloading location description";
	        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	        //TODO: error dialog, toast
        }
    }
    //</editor-fold>

    private void updateFormFields(Location location) {
        getLocationNameEditText().setText(location.getName());
        getLocationDescriptionEditText().setText(location.getDescription());
        getLocationAddressCityEditText().setText(location.getAddress().getCity());
        getLocationAddressCountryEditText().setText(location.getAddress().getCountry());
        getLocationAddressPostalCodeEditText().setText(location.getAddress().getPostalCode());
        getLocationAddressStreetEditText().setText(location.getAddress().getStreet());
        getGoogleMapsManagementService().setMapPosition(getGoogleMap(), getGoogleMapsManagementService().getLatLngFromLocation(location), 15);
    }

}
package pl.edu.agh.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.activities.locations.ShowLocationsActivity;
import pl.edu.agh.activities.ShowTripsActivity;
import pl.edu.agh.activities.SynchronizationActivity;
import pl.edu.agh.activities.help.HelpActivity;
import pl.edu.agh.activities.locations.AddLocationActivity;
import pl.edu.agh.activities.locations.ShowAllLocationsOnMapActivity;
import pl.edu.agh.activities.locations.ShowPrivateLocationsOnMapActivity;
import pl.edu.agh.activities.settings.SettingsActivity;
import pl.edu.agh.activities.tripcreator.TripCreatorActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.SynchronizationService;
import pl.edu.agh.services.implementation.UserAccountManagementService;

/**
 * Created by Krzysiu on 2014-06-10.
 */
public class MainMenuActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

    //<editor-fold desc="Lifecycle Methods - onCreate">
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        getActionBar().hide();

	    // TODO: move to appropriate place
        //new SynchronizationService(this).downloadAllLocations();

        try {
/*            Location origin = new Location();
            origin.setLatitude(50.067265);
            origin.setLongitude(19.944448);
            Location destination = new Location();
            destination.setLatitude(50.435275);
            destination.setLongitude(18.850237);
            GoogleDirectionsSerializer route = new GoogleDirectionsService().getTripDescription(origin, destination, null, null, null);
            System.out.println("HERE");*/

/*            GoogleGeocodingSerializer geocode = new GoogleGeocodingService().getLocationDescription("santacruz", null, null);
            System.out.println("HERE");*/
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        getCreateTripButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTripAction(view);
            }
        });

        getShowAllLocationsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllLocationsOnMapAction(view);
            }
        });

        getShowMyLocationsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPrivateLocationsOnMapAction(view);
            }
        });

        getLocationsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationsAction(view);
            }
        });

        getSynchronizeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSynchronizationActivity(view);
            }
        });

        getAddLocationButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocationAction(view);
            }
        });

        getShowTripsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTripsAction(view);
            }
        });

        getShowHelpActivityButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpActivity(view);
            }
        });

        getSettingsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettings(view);
            }
        });

	    getLogoutButton().setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View view) {
			    logOut(view);
		    }
	    });

        getCreateTripButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showWizardCreatorActivity(view);
            }
        });

    }
    //</editor-fold>


    // TODO : use IBinder to call services or remove completely
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        ServiceConnection mConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                mSynchronizationService = ((SynchronizationService.LocalBinder) service).getService();
//                Toast.makeText(MainMenuActivity.this, R.string.TripCreatorInitPage_IntroductionText, Toast.LENGTH_SHORT).show();
//                mSynchronizationService.downloadAllLocations();
//                mSynchronizationService.downloadTrips();
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        };
//        bindService(new Intent(MainMenuActivity.this, SynchronizationService.LocalBinder.class), mConnection, Context.BIND_AUTO_CREATE);
//
//    }

    //<editor-fold desc="Getters and Setters - Layout Elements">
    private Button getCreateTripButton() {
        return (Button) findViewById(R.id.MainMenu_CreateTripButton);
    }

    private Button getShowMyLocationsButton() {
        return (Button) findViewById(R.id.MainMenu_ShowPrivateLocationsButton);
    }

    private Button getShowAllLocationsButton() {
        return (Button) findViewById(R.id.MainMenu_ShowAllLocationsButton);
    }

    private Button getLocationsButton() {
        return (Button) findViewById(R.id.MainMenu_LocationsButton);
    }

    private Button getSynchronizeButton() {
        return (Button) findViewById(R.id.MainMenu_SynchronizeButton);
    }

    private Button getAddLocationButton() {
        return (Button) findViewById(R.id.MainMenu_AddLocationButton);
    }

    private Button getShowTripsButton() {
        return (Button) findViewById(R.id.MainMenu_TripsButton);
    }

    private Button getShowHelpActivityButton() { return (Button) findViewById(R.id.MainMenu_HelpButton); }

    private Button getSettingsButton() { return (Button) findViewById(R.id.MainMenu_SettingsButton); }

	private Button getLogoutButton() {
		return (Button) findViewById(R.id.MainMenu_LogoutButton);
	}

	//</editor-fold>

    //<editor-fold desc="Actions">
    private void createTripAction(View view) {
    }

    private void showPrivateLocationsOnMapAction(View view) {
        startActivity(new Intent(this, ShowPrivateLocationsOnMapActivity.class));
    }

    private void showAllLocationsOnMapAction(View view) {
        startActivity(new Intent(this, ShowAllLocationsOnMapActivity.class));
    }

    private void showLocationsAction(View view) {
        startActivity(new Intent(this, ShowLocationsActivity.class));
    }

    private void addLocationAction(View view) {
        startActivity(new Intent(this, AddLocationActivity.class));
    }

    private void showTripsAction(View view) {
        startActivity(new Intent(this, ShowTripsActivity.class));
    }

    private void showHelpActivity(View view) { startActivity(new Intent(this, HelpActivity.class )); }

    private void showSettings(View view) { startActivity(new Intent(this, SettingsActivity.class ));}

    private void showSynchronizationActivity(View view) { startActivity(new Intent(this, SynchronizationActivity.class)); }

    private void showWizardCreatorActivity(View view) { startActivity(new Intent(this, TripCreatorActivity.class )); }

	private void logOut(View view) {
		new UserAccountManagementService(this).logOut();
		Intent logOutIntent = new Intent(this, LoginActivity.class);
		logOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(logOutIntent);
		finish();
	}
	//</editor-fold>
}
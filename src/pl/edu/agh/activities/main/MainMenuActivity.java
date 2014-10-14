package pl.edu.agh.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import pl.edu.agh.activities.ShowLocationsActivity;
import pl.edu.agh.activities.locations.AddLocationActivity;
import pl.edu.agh.activities.locations.ShowAllLocationsOnMapActivity;
import pl.edu.agh.activities.locations.ShowPrivateLocationsOnMapActivity;
import pl.edu.agh.asynctasks.locations.PutLocationStatusAsyncTask;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-10.
 */
public class MainMenuActivity extends Activity {

    //<editor-fold desc="Lifecycle Methods - onCreate">
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        getActionBar().hide();

        //Tests
        this.deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
        new TestDatabaseHelper(this).getReadableDatabase();

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
                try {
                    Location location = new PutLocationStatusAsyncTask(UserAccountManagementService.getToken(), 3L, Location.Status.UNAVAILABLE).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        getAddLocationButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocationAction(view);
            }
        });

    }
    //</editor-fold>

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

    private void showMyLocationsOnMapAction(View view) {
        startActivity(new Intent(this, ShowPrivateLocationsOnMapActivity.class));
    }
    //</editor-fold>


}
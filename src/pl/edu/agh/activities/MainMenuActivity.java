package pl.edu.agh.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import pl.edu.agh.asynctasks.locations.PutLocationStatusAsyncTask;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.serializers.google.directions.GoogleDirectionsSerializer;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.services.implementation.GoogleDirectionsService;
import pl.edu.agh.services.implementation.GoogleGeocodingService;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-10.
 */
public class MainMenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        //Tests
        this.deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
        new TestDatabaseHelper(this).getReadableDatabase();

        try {
            Location origin = new Location();
            origin.setLatitude(50.067265);
            origin.setLongitude(19.944448);
            Location destination = new Location();
            destination.setLatitude(50.435275);
            destination.setLongitude(18.850237);
            GoogleDirectionsSerializer route = new GoogleDirectionsService().getTripDescription(origin, destination, null, null, null);
            new InfoToastBuilder(this, "Wycieczka zostałą załadowana poprawnie.").build().show();
            System.out.println("HERE");

            GoogleGeocodingSerializer geocode = new GoogleGeocodingService().getLocationDescription("santacruz", null, null);
            new InfoToastBuilder(this, "Adres lokalizacji załadowany").build().show();
            System.out.println("HERE");
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        ((Button) findViewById(R.id.MainMenu_CreateTripButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTrip(view);
            }
        });

        ((Button) findViewById(R.id.MainMenu_ShowLocationsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationsOnMap(view);
            }
        });

        ((Button) findViewById(R.id.MainMenu_LocationsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocations(view);
            }
        });

        ((Button) findViewById(R.id.MainMenu_SynchronizeButton)).setOnClickListener(new View.OnClickListener() {
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

        ((Button) findViewById(R.id.MainMenu_AddLocationButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation(view);
            }
        });

    }

    private void createTrip(View view) {

    }


    private void showLocationsOnMap(View view) {
        startActivity(new Intent(this, ShowAllLocationsOnMapActivity.class));
    }

    private void showLocations(View view) {
        startActivity(new Intent(this, ShowLocationsActivity.class));
    }

    private void addLocation(View view) {
        startActivity(new Intent(this, AddLocationActivity.class));
    }

}
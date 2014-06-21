package pl.edu.agh.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import pl.edu.agh.asynctasks.PutLocationStatusAsyncTask;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-10.
 */
public class MainMenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

	    ((Button)findViewById(R.id.MainMenu_CreateTripButton)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    createTrip(view);
		    }
	    });

	    ((Button)findViewById(R.id.MainMenu_ShowLocationsButton)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    showLocationsOnMap(view);
		    }
	    });

	    ((Button)findViewById(R.id.MainMenu_LocationsButton)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    showLocations(view);
		    }
	    });

        ((Button)findViewById(R.id.MainMenu_SynchronizeButton)).setOnClickListener(new View.OnClickListener() {
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

        ((Button) findViewById(R.id.MainMenu_AddLocationButton)).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLocation(view);
            }
        });
    }

	private void createTrip(View view) {

	}


	private void showLocationsOnMap(View view) {
		startActivity(new Intent(this, ShowLocationsOnMapActivity.class));
	}

	private void showLocations(View view) {
		startActivity(new Intent(this, ShowLocationsActivity.class));
	}

    private void addLocation(View view) {
        startActivity(new Intent(this, AddLocationActivity.class));
    }

}
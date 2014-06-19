package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import pl.edu.agh.asynctasks.GetAllLocationsAsyncTask;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.GoogleMapsManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-11.
 */
public class ShowLocationsOnMapActivity extends Activity {

    private GoogleMapsManagementService googleMapsManagementService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_locations_on_map_activity);

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

        try {
            googleMapsManagementService = new GoogleMapsManagementService();
            List<Location> locations = new GetAllLocationsAsyncTask(UserAccountManagementService.getToken()).execute().get();
            for(Location location : locations) {
                map.addMarker(googleMapsManagementService.createLocationMarker(location));
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapsManagementService.getLatLngFromLocation(locations.get(0)), 15));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

		// Buttons

		((Button) findViewById(R.id.ShowLocations_ShowDescriptionButton)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showDescription(view);
			}
		});

		((Button) findViewById(R.id.ShowLocations_DownloadLocationsButton)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				downloadLocations(view);
			}
		});

	}

	private void showDescription(View view) {
		Toast.makeText(getApplicationContext(), "showDescription", Toast.LENGTH_SHORT).show();
	}

	private void downloadLocations(View view) {
		Toast.makeText(getApplicationContext(), "download locations", Toast.LENGTH_SHORT).show();
	}
}


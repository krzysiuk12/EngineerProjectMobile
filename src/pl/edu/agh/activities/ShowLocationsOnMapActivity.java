package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-11.
 */
public class ShowLocationsOnMapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_locations_on_map_activity);

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		// test point
		LatLng testLatLng = new LatLng(51.4895247, 0.0083485);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(testLatLng, 10));

		map.addMarker(
				new MarkerOptions()
						.title("YOU")
						.snippet("... are here ...")
						.position(testLatLng)
		);

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


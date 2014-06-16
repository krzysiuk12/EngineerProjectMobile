package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.fragments.LocationDescriptionFragment;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-15.
 */
public class LocationDescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ( findViewById(R.id.ShowLocations_LocationDescription) != null ) {
			// if in two panel-mode this activity is unnecessary
			finish();
			return;
		}

		if (savedInstanceState == null) {
			// Plug in the details fragment
			LocationDescriptionFragment details = new LocationDescriptionFragment();
			details.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		}
	}

}

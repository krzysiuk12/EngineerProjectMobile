package pl.edu.agh.activities.locations;

import android.app.Activity;
import android.os.Bundle;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-15.
 */
public class ShowLocationsActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_locations);
	}
}

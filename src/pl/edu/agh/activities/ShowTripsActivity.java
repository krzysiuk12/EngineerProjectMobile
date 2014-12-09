package pl.edu.agh.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;

/**
 * Created by Magda on 2014-12-03.
 */
public class ShowTripsActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_trips_activity);
	}

}

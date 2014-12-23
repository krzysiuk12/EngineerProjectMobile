package pl.edu.agh.activities;

import android.os.Bundle;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.fragments.AbstractDescriptionFragment;

/**
 * Created by Magda on 2014-12-04.
 */
public abstract class AbstractDetailsActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ( findViewById(getDetailsPaneId()) != null ) {
			// if in two panel-mode this activity is unnecessary
			finish();
			return;
		}

		if (savedInstanceState == null) {
			// Plug in the details fragment
			AbstractDescriptionFragment details = getDetailsFragment();
			details.setArguments(getIntent().getExtras());
			getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		}
	}

	protected abstract int getDetailsPaneId();

	protected abstract AbstractDescriptionFragment getDetailsFragment();

}

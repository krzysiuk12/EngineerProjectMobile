package pl.edu.agh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.SynchronizationService;
import pl.edu.agh.services.interfaces.ISynchronizationService;
import pl.edu.agh.tools.ErrorTools;

/**
 * Created by Magda on 2014-11-26.
 */
public class SynchronizationActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	enum SendLocationMode {
		PRIVATE,
		PUBLIC,
		BOTH
	}

	private ISynchronizationService synchronizationService;
	private SendLocationMode selectedMode = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		synchronizationService = new SynchronizationService(this);
		setContentView(R.layout.synchronization_fragment);
	}

	public void onSendLocationsRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch( view.getId() ) {
			case R.id.Synchronization_SendLocationsCheckbox_Private:
				if ( checked )
					selectedMode = SendLocationMode.PRIVATE;
					break;

			case R.id.Synchronization_SendLocationsCheckbox_Public:
				if ( checked )
					selectedMode = SendLocationMode.PUBLIC;
					break;

			case R.id.Synchronization_SendLocationsCheckbox_Both:
				if ( checked )
					selectedMode = SendLocationMode.BOTH;
					break;
		}
	}

	public void onSendLocationsButtonClicked(View view) {
		try {
			switch (selectedMode) {
				case PRIVATE:
					synchronizationService.sendNewPrivateLocations();
					break;

				case PUBLIC:
					synchronizationService.sendNewPublicLocations();
					break;

				case BOTH:
					synchronizationService.sendAllNewLocations();
					break;

			}
			new InfoToastBuilder(this, getString(R.string.Synchronization_SendLocations_Success)).build().show();
		} catch ( SynchronizationException e) {
			new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e.getExceptionDefinition()));
		}
	}

	public void onManageTripsButtonClicked(View view) {

		if ( ((CheckBox) findViewById(R.id.Synchronization_ManageTrips_Download)).isChecked() ) {
			synchronizationService.downloadTrips();
		}

		if ( ((CheckBox) findViewById(R.id.Synchronization_ManageTrips_Send)).isChecked() ) {
			try {
				synchronizationService.sendTrips();
				new InfoToastBuilder(this, getString(R.string.Synchronization_ManageTrips_Send_Success)).build().show();
			} catch (SynchronizationException e) {
				new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e.getExceptionDefinition())).build().show();
			}
		}

	}

	public void onDownloadLocationsButtonClicked(View view) {
		// show view with map
		startActivity(new Intent(this, DownloadLocationsInScopeActivity.class));
	}

}

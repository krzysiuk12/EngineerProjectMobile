package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.SynchronizationService;
import pl.edu.agh.services.interfaces.ISynchronizationService;

/**
 * Created by Magda on 2014-11-26.
 */
public class SynchronizationActivity extends Activity {

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
//		synchronizationService = new SynchronizationService(this);   // TODO
		synchronizationService = new SynchronizationService();
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
		switch ( selectedMode ) {
			case PRIVATE:
				synchronizationService.sendNewPrivateLocations();
				break;

			case PUBLIC:
				synchronizationService.sendNewPublicLocations();
				break;

			case BOTH:
				break;

		}
	}

}

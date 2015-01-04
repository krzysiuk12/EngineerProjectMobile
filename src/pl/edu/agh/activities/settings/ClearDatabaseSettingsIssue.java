package pl.edu.agh.activities.settings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.fragments.TripSelectionMode;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.LocationManagementService;
import pl.edu.agh.services.implementation.TripManagementService;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.services.interfaces.ITripManagementService;

/**
 * Created by Magda on 2015-01-03.
 */
public class ClearDatabaseSettingsIssue extends SettingsIssue {

	private boolean clearPublic = false;
	private boolean clearPrivate = false;
	private TripSelectionMode tripsToDelete = TripSelectionMode.ALL_TRIPS;

	private ITripManagementService tripManagementService;
	private ILocationManagementService locationManagementService;

	public ClearDatabaseSettingsIssue(String label) {
		super(label, R.layout.settings_clear_database);
	}

	@Override
	public void initializeView(View view) {

		tripManagementService = new TripManagementService(view.getContext());
		locationManagementService = new LocationManagementService(view.getContext());

		((CheckBox) view.findViewById(R.id.Settings_Database_Private)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				clearPrivate = isChecked;
			}
		});

		((CheckBox) view.findViewById(R.id.Settings_Database_Public)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				clearPublic = isChecked;
			}
		});

		ArrayAdapter<TripSelectionMode> adapter = new ArrayAdapter<TripSelectionMode>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, TripSelectionMode.values());
		Spinner tripsSpinner = ((Spinner) view.findViewById(R.id.Settings_Database_TripsSpinner));
		tripsSpinner.setAdapter(adapter);
		tripsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				tripsToDelete = (TripSelectionMode) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				tripsToDelete = TripSelectionMode.ALL_TRIPS;
			}
		});

		((Button) view.findViewById(R.id.Settings_Database_Button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearDatabase();
			}
		});

	}

	private void clearDatabase() {
		try {
			tripManagementService.deleteTrips(UserAccountManagementService.getUserAccount(), tripsToDelete);

			if (clearPublic) {
				locationManagementService.deletePublicLocations();
			}

			if (clearPrivate) {
				locationManagementService.deletePrivateLocations(UserAccountManagementService.getUserAccount());
			}

			getFragment().showSuccessToast(R.string.Settings_Database_Success);
		} catch ( TripException | LocationException e ) {
			e.printStackTrace();
			getFragment().showErrorToast(R.string.Settings_Database_Failure);
		}
	}

}

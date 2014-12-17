package pl.edu.agh.activities.settings;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

/**
 * Created by Magda on 2014-12-16.
 */
public class DefaultUserSettingsIssue extends SettingsIssue {

	private boolean isDefault = false;  // TODO: make it load data from DB

	public DefaultUserSettingsIssue(String label) {
		super(label, R.layout.settings_default_user);
	}

	@Override
	public void initializeView(View view) {

		((CheckBox) view.findViewById(R.id.Settings_DefaultUser_Checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isDefault = isChecked;
			}
		});

		((Button) view.findViewById(R.id.Settings_DefaultUser_Button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new UserAccountManagementService(v.getContext()).setAsDefaultUser(UserAccountManagementService.getUserAccount(), isDefault);
				getFragment().showSuccessToastAndFinish();
			}
		});
	}
}

package pl.edu.agh.activities.settings;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.fragments.SettingsIssuePanelFragment;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.ApplicationSettingsService;
import pl.edu.agh.services.implementation.UserAccountManagementService;

/**
 * Created by Magda on 2014-11-19.
 */
public class LanguageSettingsIssue extends SettingsIssue {

	private UserAccount.Language currentLanguage;

	public LanguageSettingsIssue(String label) {
		super(label, R.layout.settings_language);
	}

	@Override
	public void initializeView(View view) {
		configureLanguageSpinner(view);

		((Button) view.findViewById(R.id.Settings_Language_Button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLanguageChange(v);
			}
		});
	}

	private void configureLanguageSpinner(View view) {
		Spinner languageSpinner = ((Spinner) view.findViewById(R.id.Settings_Language_Spinner));
		ArrayAdapter<UserAccount.Language> adapter = new ArrayAdapter<UserAccount.Language>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, UserAccount.Language.values());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		languageSpinner.setAdapter(adapter);

		languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				currentLanguage = (UserAccount.Language) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				currentLanguage = UserAccount.Language.EN;
			}
		});
	}

	private void onLanguageChange(View view) {
		// TODO: combine this into one method!
		new ApplicationSettingsService().changeLanguagePreference(view, currentLanguage);
		new UserAccountManagementService(view.getContext()).changeLanguagePreferenceForUser(UserAccountManagementService.getUserAccount(), currentLanguage);
		getFragment().showSuccessToastAndFinish();
	}

}

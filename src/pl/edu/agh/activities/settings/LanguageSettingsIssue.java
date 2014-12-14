package pl.edu.agh.activities.settings;

import android.content.res.Configuration;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.util.Locale;

/**
 * Created by Magda on 2014-11-19.
 */
public class LanguageSettingsIssue extends SettingsIssue {

	public enum Language {
		EN,
		PL
	}

	private Language currentLanguage;

	public LanguageSettingsIssue(String label) {
		super(label, R.layout.settings_language);
	}

	@Override
	public void initializeView(View view) {
		super.initializeView(view);

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
		ArrayAdapter<Language> adapter = new ArrayAdapter<Language>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, Language.values());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		languageSpinner.setAdapter(adapter);

		languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				currentLanguage = (Language) parent.getItemAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				currentLanguage = Language.EN;
			}
		});
	}

	private void onLanguageChange(View view) {
		Locale locale = new Locale(currentLanguage.toString());
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		view.getContext().getResources().updateConfiguration(config, view.getContext().getResources().getDisplayMetrics());
		// TODO: save setting in databse
	}

}

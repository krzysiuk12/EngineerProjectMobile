package pl.edu.agh.services.implementation;

import android.content.Context;
import android.content.res.Configuration;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.main.R;
import pl.edu.agh.services.interfaces.IApplicationSettingsService;

import java.util.Locale;

/**
 * Created by Magda on 2014-12-16.
 */
public class ApplicationSettingsService implements IApplicationSettingsService {

	@Override
	public void changeLanguagePreference(Context context, UserAccount.Language language) {
		Locale locale = new Locale(language.toString());
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

}

package pl.edu.agh.services.interfaces;

import android.view.View;
import pl.edu.agh.domain.accounts.UserAccount;

/**
 * Created by Magda on 2014-12-16.
 */
public interface IApplicationSettingsService {

	public void changeLanguagePreference(View view, UserAccount.Language language);

}

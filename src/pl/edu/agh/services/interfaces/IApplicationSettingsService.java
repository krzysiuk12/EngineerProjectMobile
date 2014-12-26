package pl.edu.agh.services.interfaces;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.accounts.UserAccount;

/**
 * Created by Magda on 2014-12-16.
 */
public interface IApplicationSettingsService {

	public void changeLanguagePreference(Context context, UserAccount.Language language);

}

package pl.edu.agh.services;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.ApplicationSettingsService;
import pl.edu.agh.services.interfaces.IApplicationSettingsService;

/**
 * Created by Magda on 2014-12-27.
 */
public class ApplicationSettingsServiceTest extends AndroidTestCase {

	private IApplicationSettingsService applicationSettingsService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		applicationSettingsService = new ApplicationSettingsService();
	}

	@UiThreadTest
	public void testChangeLanguagePreference() throws Exception {
		assertEquals("City", getContext().getResources().getString(R.string.Address_City));
		applicationSettingsService.changeLanguagePreference(getContext(), UserAccount.Language.PL);
		assertEquals("Miasto", getContext().getResources().getString(R.string.Address_City));
	}
}

package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.exceptions.SynchronizationException;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public interface IUserAccountManagementService {

	public boolean logIn(String login, String password) throws SynchronizationException;

	public boolean logAsDefault() throws SynchronizationException;

	void logOut(String token) throws SynchronizationException;

	public void saveUserAccount(UserAccount userAccount);

	public void updateUserAccount(UserAccount userAccount);

	public UserAccount getUserAccountByLogin(String login);

	public UserAccount getUserAccountByToken(String token);

	UserAccount getDefaultUser();

	public void setAsDefaultUser(UserAccount user, boolean isDefault);

	public void changeLanguagePreferenceForUser(UserAccount user, UserAccount.Language language);

}

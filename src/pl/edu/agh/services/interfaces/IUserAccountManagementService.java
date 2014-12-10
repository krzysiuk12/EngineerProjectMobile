package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public interface IUserAccountManagementService {

	public boolean logIn(String login, String password);

	public void logOut();

	public void saveUserAccount(UserAccount userAccount);

	public UserAccount getUserAccountByLogin(String login);

	public UserAccount getUserAccountByToken(String token);

}

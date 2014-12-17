package pl.edu.agh.repositories.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;

import java.util.List;

/**
 * Created by Magda on 2014-12-10.
 */
public interface IUserAccountRepository {

	public void saveUserAccount(UserAccount userAccount);

	public void updateUserAccount(UserAccount userAccount);

	public UserAccount getUserAccountByLogin(String login);

	public UserAccount getUserAccountByToken(String token);

	public UserAccount getDefaultUser();

	public List<UserAccount> getAllUsers();

}

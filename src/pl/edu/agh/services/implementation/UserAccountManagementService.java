package pl.edu.agh.services.implementation;

import android.content.Context;
import pl.edu.agh.asynctasks.authorization.LogInAsyncTask;
import pl.edu.agh.asynctasks.authorization.LogOutAsyncTask;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.repositories.implementation.OrmLiteUserAccountRepository;
import pl.edu.agh.repositories.interfaces.IUserAccountRepository;
import pl.edu.agh.serializers.LoginSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class UserAccountManagementService extends BaseService implements IUserAccountManagementService {

    private static String TOKEN = "token";

	private static UserAccount USER_ACCOUNT = null;

	private IUserAccountRepository userAccountRepository;

	public UserAccountManagementService() {
		userAccountRepository = new OrmLiteUserAccountRepository(getHelper());
	}

	public UserAccountManagementService(Context context) {
		userAccountRepository = new OrmLiteUserAccountRepository(getHelperInternal(context));
	}

    public static String getToken() {
        return TOKEN;
    }

	public static UserAccount getUserAccount() {
		return USER_ACCOUNT;
	}

	public boolean logIn(String login, String password) {
		try {
			ResponseSerializer<LoginSerializer> response = new LogInAsyncTask(login, password).execute().get();
			if ( response.getStatus() == ResponseStatus.OK ) {
				TOKEN = response.getResult().getToken();
				saveUserInSession(login, password);
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// todo: error handling : return List<ErrorMessage> ?
		return false;
	}

	public void logOut() {
		new LogOutAsyncTask(getToken()).execute();
	}

	@Override
	public void saveUserAccount(UserAccount userAccount) {
		userAccountRepository.saveUserAccount(userAccount);
	}

	@Override
	public UserAccount getUserAccountByLogin(String login) {
		return userAccountRepository.getUserAccountByLogin(login);
	}

	@Override
	public UserAccount getUserAccountByToken(String token) {
		return userAccountRepository.getUserAccountByToken(token);
	}

	// <editor-fold description="Private methods">

	private void saveUserInSession(String login, String password) {
		USER_ACCOUNT = userAccountRepository.getUserAccountByLogin(login);
		if ( USER_ACCOUNT == null ) {
			// save in database - first login
			USER_ACCOUNT = createUserAccount(login, password);
			userAccountRepository.saveUserAccount(USER_ACCOUNT);
		} else {
			// update token in database
			USER_ACCOUNT.setToken(getToken());
			userAccountRepository.updateUserAccount(USER_ACCOUNT);
		}
	}

	private UserAccount createUserAccount(String login, String password) {
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(login);
		userAccount.setPassword(password);
		userAccount.setToken(getToken());
		return userAccount;
	}

	//</editor-fold>

}

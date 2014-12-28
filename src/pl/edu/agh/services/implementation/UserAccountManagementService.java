package pl.edu.agh.services.implementation;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import pl.edu.agh.asynctasks.authorization.LogInAsyncTask;
import pl.edu.agh.asynctasks.authorization.LogOutAsyncTask;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.repositories.implementation.OrmLiteUserAccountRepository;
import pl.edu.agh.repositories.interfaces.IUserAccountRepository;
import pl.edu.agh.serializers.LoginSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
import pl.edu.agh.services.interfaces.IApplicationSettingsService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class UserAccountManagementService extends BaseService implements IUserAccountManagementService {

    private static String TOKEN = "token";

	private static UserAccount USER_ACCOUNT = null;

	private IApplicationSettingsService applicationSettingsService;

	private IUserAccountRepository userAccountRepository;

	public UserAccountManagementService() {
		userAccountRepository = new OrmLiteUserAccountRepository(getHelper());
		applicationSettingsService = new ApplicationSettingsService();
	}

	public UserAccountManagementService(Context context) {
		userAccountRepository = new OrmLiteUserAccountRepository(getHelperInternal(context));
		applicationSettingsService = new ApplicationSettingsService();
	}

	public UserAccountManagementService(OrmLiteSqliteOpenHelper helper) {
		userAccountRepository = new OrmLiteUserAccountRepository(helper);
		applicationSettingsService = new ApplicationSettingsService();
	}

    public static String getToken() {
        return TOKEN;
    }

	public static UserAccount getUserAccount() {
		return USER_ACCOUNT;
	}

	public boolean logIn(String login, String password) throws SynchronizationException {
		try {
			ResponseSerializer<LoginSerializer> response = new LogInAsyncTask(login, password).execute().get();
			if ( validateServerResponse(response) ) {
				TOKEN = response.getResult().getToken();
				saveUserInSession(login, password);
				return true;
			}
		} catch (RestClientException e ) {
			getLogService().error(e.toString());
			throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_UNREACHABLE);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_UNREACHABLE);
		} catch (ExecutionException e) {
			e.printStackTrace();
			throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_UNREACHABLE);
		}
		return false;
	}

	@Override
	public boolean logAsDefault() throws SynchronizationException {
		UserAccount defaultUser = getDefaultUser();
		if ( defaultUser != null)
			return logIn(defaultUser.getLogin(), defaultUser.getPassword());
		else
			return false;
	}

	@Override
	public void logOut() {
		new LogOutAsyncTask(getToken()).execute();
	}

	@Override
	public void saveUserAccount(UserAccount userAccount) {
		userAccountRepository.saveUserAccount(userAccount);
	}

	@Override
	public void updateUserAccount(UserAccount userAccount) {
		userAccountRepository.updateUserAccount(userAccount);
	}

	@Override
	public UserAccount getUserAccountByLogin(String login) {
		return userAccountRepository.getUserAccountByLogin(login);
	}

	@Override
	public UserAccount getUserAccountByToken(String token) {
		return userAccountRepository.getUserAccountByToken(token);
	}

	@Override
	public UserAccount getDefaultUser() {
		return userAccountRepository.getDefaultUser();
	}

	@Override
	public void setAsDefaultUser(UserAccount user, boolean isDefault) {
		if ( isDefault ) {
			// set other users to false - only one user can be a default user
			List<UserAccount> userAccounts = userAccountRepository.getAllUsers();

			for (UserAccount userAccount : userAccounts) {
				userAccount.setDefaultUser(false);
				updateUserAccount(userAccount);
			}
		}

		user.setDefaultUser(isDefault);
		updateUserAccount(user);
	}

	@Override
	public void changeLanguagePreferenceForUser(UserAccount user, UserAccount.Language language) {
		user.setLanguage(language);
		updateUserAccount(user);
	}

	// <editor-fold description="Private methods">

	private void saveUserInSession(String login, String password) {
		USER_ACCOUNT = userAccountRepository.getUserAccountByLogin(login);
		if ( USER_ACCOUNT == null ) {
			// save in database - first login
			USER_ACCOUNT = createUserAccount(login, password);
			saveUserAccount(USER_ACCOUNT);
		} else {
			// update token in database
			USER_ACCOUNT.setToken(getToken());
			updateUserAccount(USER_ACCOUNT);
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

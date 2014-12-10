package pl.edu.agh.services.implementation;

import pl.edu.agh.asynctasks.authorization.LogInAsyncTask;
import pl.edu.agh.asynctasks.authorization.LogOutAsyncTask;
import pl.edu.agh.serializers.LoginSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class UserAccountManagementService implements IUserAccountManagementService {

    private static String TOKEN = "token";

	private static String LOGIN = "";

    public static String getToken() {
        return TOKEN;
    }

	public static String getLogin() {
		return LOGIN;
	}

	public boolean logIn(String login, String password) {
		try {
			ResponseSerializer<LoginSerializer> response = new LogInAsyncTask(login, password).execute().get();
			if ( response.getStatus() == ResponseStatus.OK ) {
				TOKEN = response.getResult().getToken();
				LOGIN = response.getResult().getLogin();
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

}

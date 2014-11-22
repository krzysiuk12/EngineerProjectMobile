package pl.edu.agh.services.interfaces;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public interface IUserAccountManagementService {

	public boolean logIn(String login, String password);

	public void logOut();

}

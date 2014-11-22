package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Magda on 2014-11-22.
 */
public class SessionPathBuilder extends PathBuilder {

	protected static final String SESSION_PATH = SERVER_PATH + "/sessions";

	public String buildLogInPath() {
		return SESSION_PATH + "/login";
	}

	public String buildLogOutPath() {
		return SESSION_PATH + "/logout";
	}

}

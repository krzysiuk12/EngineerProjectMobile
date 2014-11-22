package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Magda on 2014-11-22.
 */
public class TripsPathBuilder extends PathBuilder {

	protected static final String TRIPS_PATH = SERVER_PATH + "/trips";

	public String buildMyTripsPath() {
		return TRIPS_PATH + "/my";
	}

}

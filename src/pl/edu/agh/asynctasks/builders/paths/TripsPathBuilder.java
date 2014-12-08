package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Magda on 2014-11-22.
 */
public class TripsPathBuilder extends PathBuilder {

	protected static final String TRIPS_PATH = SERVER_PATH + "/trips";

	protected static final String TRIP_DAY_INFIX = "/day";

	protected static final String ALL_DETAIL_SUFFIX = "/all";

	public String buildAllTripsPath() {
		return TRIPS_PATH;
	}

	public String buildMyTripsPath() {
		return TRIPS_PATH + "/my";
	}

	public String buildTripDayDetailsPath(long id) {
		return TRIPS_PATH + TRIP_DAY_INFIX + "/" + id;
	}

	public String buildAllTripDayDetailsPath(long id) {
		return TRIPS_PATH + TRIP_DAY_INFIX + "/" + id + ALL_DETAIL_SUFFIX;
	}

	public String buildAddNewTripPath() {
		return TRIPS_PATH;
	}

}

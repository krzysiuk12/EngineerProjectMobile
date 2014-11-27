package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Magda on 2014-11-25.
 */
public class CoordinatesPathBuilder extends PathBuilder {

	private static final String COORDINATES_PATH = "/coordinates";
	private static final String LOCATIONS_SUFFIX = "/locations";

	public String buildLocationsInAreaPath(double latitue, double longitude) {
		return COORDINATES_PATH + "/" + latitue + "/" + longitude + LOCATIONS_SUFFIX;
	}

	public String buildLocationsInAreaInScopePath(double latitue, double longitude, double scope) {
		return COORDINATES_PATH + "/" + latitue + "/" + longitude + "/" + scope + LOCATIONS_SUFFIX;
	}

}

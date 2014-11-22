package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Magda on 2014-11-22.
 */
public class GooglePathBuilder extends PathBuilder {

	protected static final String GOOGLE_PATH = SERVER_PATH + "/googleapi/geocode";

	public String buildLocationByAddressPath() {
		return GOOGLE_PATH + "/location";
	}

	public String buildLocationByIpPath() {
		return GOOGLE_PATH + "/ip";
	}

	public String buildLocationCoordinatesByAddressPath() {
		return GOOGLE_PATH + "/coordinates";
	}
}

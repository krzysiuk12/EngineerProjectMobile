package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class LocationsPathBuilder extends PathBuilder {

    protected static final String LOCATIONS_PATH = SERVER_PATH + "/locations";

    public String buildAllLocationsPath() {
        return LOCATIONS_PATH;
    }

    public String buildLocationByIdPath(Long id) {
        return LOCATIONS_PATH + "/" + id;
    }

    public String buildChangeLocationStatusPath(Long id) {
        return LOCATIONS_PATH + "/" + id + "/status";
    }

    public String buildAddNewLocationPath() {
        return LOCATIONS_PATH;
    }

	public String buildAllPrivateLocationsPath() {
		return LOCATIONS_PATH + "/my";
	}

	public String buildPrivateLocationByIdPath(long id) {
		return LOCATIONS_PATH + "/my/" + id;
	}

	public String buildAddNewPrivateLocationPath() {
		return LOCATIONS_PATH + "/private";
	}

}

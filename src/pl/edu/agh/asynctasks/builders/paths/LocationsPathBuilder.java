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
        return new StringBuilder().append(LOCATIONS_PATH).append("/").append(id).toString();
    }

    public String buildChangeLocationStatusPath(Long id) {
        return new StringBuilder(LOCATIONS_PATH).append("/").append(id).append("/status").toString();
    }

    public String buildAddNewLocationPath() {
        return LOCATIONS_PATH;
    }

}

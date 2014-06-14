package pl.edu.agh.tools;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class PathTools {

    public static final String SERVER_PATH = "http://192.168.0.16:8080/TourTrip";
    public static final String LOCATIONS_PATH = SERVER_PATH + "/locations";
    public static final String USERS_PATH = SERVER_PATH + "/users";

    public static String getLocationByIdPath(Long id) {
        return new StringBuilder(LOCATIONS_PATH).append("/").append(id).toString();
    }
}

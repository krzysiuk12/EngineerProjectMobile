package pl.edu.agh.asynctasks.builders.paths;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class GoogleDirectionsApiPathBuilder extends PathBuilder {

    private enum RequestParameter {
        ORIGIN,
        DESTINATION,
        UNITS,
        MODE,
        WAYPOINTS
    }

    private static final String GOOGLE_DIRECTIONS_PATH = "https://maps.googleapis.com/maps/api/directions/json";

    public GoogleDirectionsApiPathBuilder(String originName, String destinationName) {
        pathBuilder.append(GOOGLE_DIRECTIONS_PATH);
        appendRequestParameter(RequestParameter.ORIGIN, originName);
        appendRequestParameter(RequestParameter.DESTINATION, destinationName);
    }

    public GoogleDirectionsApiPathBuilder(double originLatitude, double originLongitude, double destinationLatitude, double destinationLongitude) {
        pathBuilder.append(GOOGLE_DIRECTIONS_PATH);
        appendRequestParameter(RequestParameter.ORIGIN, Double.toString(originLatitude), Double.toString(originLongitude));
        appendRequestParameter(RequestParameter.DESTINATION, Double.toString(destinationLatitude), Double.toString(destinationLongitude));
    }

    public GoogleDirectionsApiPathBuilder addUnitParam(String paramValue) {
        appendRequestParameter(RequestParameter.UNITS, paramValue);
        return this;
    }

    public GoogleDirectionsApiPathBuilder addModeParam(String paramValue) {
        appendRequestParameter(RequestParameter.MODE, paramValue);
        return this;
    }

    public GoogleDirectionsApiPathBuilder addWaypointsParam(String paramValue) {
        appendRequestParameter(RequestParameter.WAYPOINTS, paramValue);
        return this;
    }

    public GoogleDirectionsApiPathBuilder addUnitParam(DistanceUnit distanceUnit) {
        appendRequestParameter(RequestParameter.UNITS, distanceUnit.name());
        return this;
    }

    public GoogleDirectionsApiPathBuilder addModeParam(TravelMode travelMode) {
        appendRequestParameter(RequestParameter.MODE, travelMode.name());
        return this;
    }

    public GoogleDirectionsApiPathBuilder addWaypointsParam(Collection<? extends Location> locations) {
        appendRequestParameter(RequestParameter.WAYPOINTS, "");
        for(Location location : locations) {
            pathBuilder.append(location.getLatitude()).append(",").append(location.getLongitude()).append("|");
        }
        pathBuilder.replace(pathBuilder.toString().length() - 1, pathBuilder.toString().length(), "");
        return this;
    }

    private void appendRequestParameter(RequestParameter param, String paramValue) {
        super.appendRequestParameter(param.name(), paramValue);
    }

    private void appendRequestParameter(RequestParameter param, String... paramValues) {
        super.appendRequestParameter(param.name(), paramValues);
    }
}

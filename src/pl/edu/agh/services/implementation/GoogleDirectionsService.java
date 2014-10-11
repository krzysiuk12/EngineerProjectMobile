package pl.edu.agh.services.implementation;

import pl.edu.agh.asynctasks.builders.paths.GoogleDirectionsApiPathBuilder;
import pl.edu.agh.asynctasks.google.GoogleDirectionsAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.GoogleDirectionsException;
import pl.edu.agh.serializers.google.directions.GoogleDirectionsSerializer;
import pl.edu.agh.serializers.google.directions.Leg;
import pl.edu.agh.serializers.google.directions.Route;
import pl.edu.agh.services.interfaces.IGoogleDirectionsService;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class GoogleDirectionsService extends BaseService implements IGoogleDirectionsService {

    @Override
    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, Collection<? extends Location> waypoints) throws GoogleDirectionsException {
        try {
            GoogleDirectionsApiPathBuilder pathBuilder = new GoogleDirectionsApiPathBuilder(origin.getLatitude(), origin.getLongitude(), destination.getLatitude(), destination.getLongitude());
            if(mode != null) {
                pathBuilder.addModeParam(mode);
            }
            if(unit != null) {
                pathBuilder.addUnitParam(unit);
            }
            if(waypoints != null && !waypoints.isEmpty()) {
                pathBuilder.addWaypointsParam(waypoints);
            }
            return new GoogleDirectionsAsyncTask(pathBuilder.build()).execute().get();
        } catch(InterruptedException ex) {
            getLogService().error("", "Failed to getTripDescription.", ex);
            return null;
        } catch (ExecutionException ex) {
            getLogService().error("", "Failed to getTripDescription.", ex);
            return null;
        }
    }

    @Override
    public Trip deserializeTripDescription(GoogleDirectionsSerializer serializer) throws GoogleDirectionsException {
        Route route = serializer.getRoutes().get(0);
        for(Leg leg : route.getLegs()) {

        }
        return null;
    }
}

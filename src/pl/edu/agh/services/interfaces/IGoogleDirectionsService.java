package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.GoogleDirectionsException;
import pl.edu.agh.serializers.google.directions.GoogleDirectionsSerializer;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public interface IGoogleDirectionsService {

    public GoogleDirectionsSerializer getTripDescription(Location origin, Location destination, TravelMode mode, DistanceUnit unit, Collection<? extends Location> waypoints) throws GoogleDirectionsException;

    public Trip deserializeTripDescription(GoogleDirectionsSerializer serializer) throws GoogleDirectionsException;

}

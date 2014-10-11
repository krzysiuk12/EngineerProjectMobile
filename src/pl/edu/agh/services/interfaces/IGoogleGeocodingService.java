package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public interface IGoogleGeocodingService {

    public GoogleGeocodingSerializer getLocationDescription(String address, String region, String components) throws GoogleGeocodingException;

    public Location deserializeLocationDescription(GoogleGeocodingSerializer serializer) throws GoogleGeocodingException;

    public Location deserializeLocationForLatLng(GoogleGeocodingSerializer serializer) throws GoogleGeocodingException;

}

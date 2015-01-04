package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.exceptions.SynchronizationException;

/**
 * Created by Magda on 2015-01-04.
 */
public interface IGoogleGeocodeService {

	public Location getLocationByAddress(String token, String address, String region, String components) throws SynchronizationException, GoogleGeocodingException;

	public Location getCoordinatesByAddress(String token, String address, String region, String components) throws SynchronizationException, GoogleGeocodingException;
}

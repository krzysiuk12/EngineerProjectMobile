package pl.edu.agh.services.implementation;

import pl.edu.agh.asynctasks.google.GetCoordinatesByAddressAsyncTask;
import pl.edu.agh.asynctasks.google.GetLocationByAddressAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.GoogleGeocodeSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.interfaces.IGoogleGeocodeService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Magda on 2015-01-04.
 */
public class GoogleGeocodeService extends BaseService implements IGoogleGeocodeService {

	@Override
	public Location getLocationByAddress(String token, String address, String region, String components) throws SynchronizationException, GoogleGeocodingException {
		GoogleGeocodeSerializer googleGeocodeSerializer = new GoogleGeocodeSerializer(address, region, components);
		try {
			ResponseSerializer<Location> response = new GetLocationByAddressAsyncTask(token, googleGeocodeSerializer).execute().get();
			if ( validateServerResponse(response) ) {
				return response.getResult();
			}
		} catch(InterruptedException ex) {
			getLogService().error("Failed to getLocationDescription.", ex);
			throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
		} catch (ExecutionException ex) {
			getLogService().error("Failed to getLocationDescription.", ex);
			throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
		}

		return null;
	}

	@Override
	public Location getCoordinatesByAddress(String token, String address, String region, String components) throws SynchronizationException, GoogleGeocodingException {
		GoogleGeocodeSerializer googleGeocodeSerializer = new GoogleGeocodeSerializer(address, region, components);
		try {
			ResponseSerializer<Location> response = new GetCoordinatesByAddressAsyncTask(token, googleGeocodeSerializer).execute().get();
			if ( validateServerResponse(response) ) {
				return response.getResult();
			}
		} catch(InterruptedException ex) {
			getLogService().error("Failed to getLocationDescription.", ex);
			throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
		} catch (ExecutionException ex) {
			getLogService().error("Failed to getLocationDescription.", ex);
			throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
		}

		return null;
	}
}

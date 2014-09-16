package pl.edu.agh.services.implementation;

import pl.edu.agh.asynctasks.builders.paths.GoogleGeocodingApiPathBuilder;
import pl.edu.agh.asynctasks.google.GoogleGeocodingAsyncTask;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.services.interfaces.IGoogleGeocodingService;
import pl.edu.agh.tools.StringTools;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class GoogleGeocodingService extends BaseService implements IGoogleGeocodingService {

    @Override
    public GoogleGeocodingSerializer getLocationDescription(String address, String region, String components) throws GoogleGeocodingException {
        try {
            GoogleGeocodingApiPathBuilder pathBuilder = new GoogleGeocodingApiPathBuilder(address);
            if(!StringTools.isNullOrEmpty(region)) {
                pathBuilder.addRegionParam(region);
            }
            if(!StringTools.isNullOrEmpty(components)) {
                pathBuilder.addComponentsParam(components);
            }
            return new GoogleGeocodingAsyncTask(pathBuilder.build()).execute().get();
        } catch(InterruptedException ex) {
            getLogService().error(StringTools.concatenateString("Failed to getLocationDescription.", ex.getMessage()));
            throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
        } catch (ExecutionException ex) {
            getLogService().error(StringTools.concatenateString("Failed to getLocationDescription.", ex.getMessage()));
            throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
        }
    }

    @Override
    public Location deserializeLocationDescription(GoogleGeocodingSerializer serializer) throws GoogleGeocodingException {
        return null;
    }
}

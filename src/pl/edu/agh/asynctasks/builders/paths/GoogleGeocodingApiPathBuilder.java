package pl.edu.agh.asynctasks.builders.paths;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class GoogleGeocodingApiPathBuilder extends PathBuilder{

    private enum RequestParameter {
        ADDRESS,
        REGION,
        COMPONENTS
    }

    private static final String GOOGLE_GEOCODING_PATH = "https://maps.googleapis.com/maps/api/geocode/json";

    public GoogleGeocodingApiPathBuilder(String address) {
        pathBuilder.append(GOOGLE_GEOCODING_PATH);
        appendRequestParameter(RequestParameter.ADDRESS, address);
    }

    public GoogleGeocodingApiPathBuilder addRegionParam(String region) {
        appendRequestParameter(RequestParameter.REGION, region);
        return this;
    }

    public GoogleGeocodingApiPathBuilder addComponentsParam(String components) {
        appendRequestParameter(RequestParameter.COMPONENTS, components);
        return this;
    }

    private void appendRequestParameter(RequestParameter param, String paramValue) {
        super.appendRequestParameter(param.name(), paramValue);
    }

    private void appendRequestParameter(RequestParameter param, String... paramValues) {
        super.appendRequestParameter(param.name(), paramValues);
    }
}

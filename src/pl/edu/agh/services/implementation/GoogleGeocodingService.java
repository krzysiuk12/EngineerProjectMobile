package pl.edu.agh.services.implementation;

import android.util.Log;
import pl.edu.agh.asynctasks.builders.paths.GoogleGeocodingApiPathBuilder;
import pl.edu.agh.asynctasks.google.GoogleGeocodingAsyncTask;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.GoogleGeocodingException;
import pl.edu.agh.serializers.google.geocoding.AddressComponent;
import pl.edu.agh.serializers.google.geocoding.AddressComponentType;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;
import pl.edu.agh.serializers.google.geocoding.Result;
import pl.edu.agh.services.interfaces.IGoogleGeocodingService;
import pl.edu.agh.tools.StringTools;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class GoogleGeocodingService extends BaseService implements IGoogleGeocodingService {

    private class AddressInformationComponents {

        private static final String ESTABLISHMENT_TYPE = "establishment";
        private static final String STREET_NUMBER_TYPE = "street_number";
        private static final String ROUTE_TYPE = "route";
        private static final String LOCALITY_TYPE = "locality";
        private static final String COUNTRY_TYPE = "country";
        private static final String POSTAL_CODE_TYPE = "postal_code";

        private String establishment;
        private String streetNumber;
        private String route;

        public String getEstablishment() {
            return establishment;
        }

        public void setEstablishment(String establishment) {
            this.establishment = establishment;
        }

        public String getStreetNumber() {
            return streetNumber;
        }

        public void setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
        }

        public String getRoute() {
            return route;
        }

        public void setRoute(String route) {
            this.route = route;
        }

    }

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
            getLogService().error("Failed to getLocationDescription.", ex);
            throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
        } catch (ExecutionException ex) {
            getLogService().error("Failed to getLocationDescription.", ex);
            throw new GoogleGeocodingException(GoogleGeocodingException.PredefinedExceptions.FAILED_TO_GET_LOCATION_DESCRIPTION);
        }
    }

    @Override
    public Location deserializeLocationDescription(GoogleGeocodingSerializer serializer) throws GoogleGeocodingException {
        Location location = new Location();
        location.setAddress(new Address());
        AddressInformationComponents aIC = new AddressInformationComponents();
        if(serializer != null && serializer.getResults() != null && !serializer.getResults().isEmpty()) {
            Result result = serializer.getResults().get(0);
            for(AddressComponent addressComponent : result.getAddress_components()) {
                if(addressComponent.getTypes().contains(AddressComponentType.establishment)) {
                    aIC.setEstablishment(addressComponent.getLong_name());
                } else if(addressComponent.getTypes().contains(AddressComponentType.street_number)) {
                    aIC.setStreetNumber(addressComponent.getLong_name());
                } else if(addressComponent.getTypes().contains(AddressComponentType.route)) {
                    aIC.setRoute(addressComponent.getLong_name());
                } else if(addressComponent.getTypes().contains(AddressComponentType.locality)) {
                    location.getAddress().setCity(addressComponent.getLong_name());
                } else if(addressComponent.getTypes().contains(AddressComponentType.postal_code)) {
                    location.getAddress().setPostalCode(addressComponent.getLong_name());
                } else if(addressComponent.getTypes().contains(AddressComponentType.country)) {
                    location.getAddress().setCountry(addressComponent.getLong_name());
                }
            }
            location.setLatitude(serializer.getResults().get(0).getGeometry().getLocation().getLat());
            location.setLongitude(serializer.getResults().get(0).getGeometry().getLocation().getLng());
            location.setName(aIC.getEstablishment() != null ? aIC.getEstablishment() : location.getAddress().getCity());
            location.setDescription(aIC.getEstablishment() != null ? aIC.getEstablishment() : location.getAddress().getCity());
            location.getAddress().setStreet(aIC.getRoute() + (aIC.getStreetNumber() != null ? " " + aIC.getStreetNumber() : ""));
        }
        return location;
    }

    @Override
    public Location deserializeLocationForLatLng(GoogleGeocodingSerializer serializer) throws GoogleGeocodingException {
        Location location = new Location();
        if(serializer != null && serializer.getResults() != null && !serializer.getResults().isEmpty()) {
            location.setLatitude(serializer.getResults().get(0).getGeometry().getLocation().getLat());
            location.setLongitude(serializer.getResults().get(0).getGeometry().getLocation().getLng());
        }
        return location;
    }
}

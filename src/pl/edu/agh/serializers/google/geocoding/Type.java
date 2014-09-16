package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum Type {

    STREET_ADDRESS,
    POLITICAL,
    COUNTRY,
    LOCALITY,
    ADMINISTRATIVE_AREA_LEVEL_1

}

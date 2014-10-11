package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponent {

    private String long_name;
    private String short_name;
    private List<AddressComponentType> types;

    public AddressComponent() {
    }

    public AddressComponent(String long_name, String short_name, List<AddressComponentType> types) {
        this.long_name = long_name;
        this.short_name = short_name;
        this.types = types;
    }

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<AddressComponentType> getTypes() {
        return types;
    }

    public void setTypes(List<AddressComponentType> types) {
        this.types = types;
    }
}

package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    private List<AddressComponent> address_components;
    private String formatted_address;
    private Geometry geometry;
/*    private List<Type> types;*/

    public Result() {
    }

    public Result(List<AddressComponent> address_components, String formatted_address, Geometry geometry) { //, List<Type> types) {
        this.address_components = address_components;
        this.formatted_address = formatted_address;
        this.geometry = geometry;
        //this.types = types;
    }

    public List<AddressComponent> getAddress_components() {
        return address_components;
    }

    public void setAddress_components(List<AddressComponent> address_components) {
        this.address_components = address_components;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

/*    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }*/
}

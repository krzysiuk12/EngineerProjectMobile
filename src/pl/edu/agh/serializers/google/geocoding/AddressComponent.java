package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponent {

    private String long_name;
    private String short_name;
    //private List<Type> types;

    public AddressComponent() {
    }

    public AddressComponent(String long_name, String short_name) { //,List<Type> types) {
        this.long_name = long_name;
        this.short_name = short_name;
        //this.types = types;
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

/*    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }*/
}

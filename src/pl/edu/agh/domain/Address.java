package pl.edu.agh.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Sławomir on 12.06.14.
 */

@DatabaseTable(tableName = "Addresses")
public class Address {

    @DatabaseField(columnName = "street")
    private String street;

    @DatabaseField(columnName = "postalCode")
    private String postalCode;

    @DatabaseField(columnName = "city")
    private String city;

    @DatabaseField(columnName = "country")
    private String country;

    public Address() {
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "Address{" +
                "street=" + street +
                ", postalCode=" + postalCode +
                ", city=" + city +
                ", country=" + country +
                "}\n";
    }
}

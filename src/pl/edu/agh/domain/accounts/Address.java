package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Sławomir on 12.06.14.
 */

@DatabaseTable(tableName = "Addresses")
public class Address implements Serializable{

    @DatabaseField(generatedId = true, index = true)
    private Long id;

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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", street=" + street +
                ", postalCode=" + postalCode +
                ", city=" + city +
                ", country=" + country +
                "}\n";
    }
}

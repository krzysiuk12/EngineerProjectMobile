package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.locations.AddressMapping;

import java.io.Serializable;

/**
 * Created by Sławomir on 12.06.14.
 */

@DatabaseTable(tableName = AddressMapping.TABLE_NAME)
public class Address implements Serializable{

    @DatabaseField(generatedId = true, index = true)
    private int id;

    @DatabaseField(columnName = AddressMapping.STREET_COLUMN_NAME)
    private String street;

    @DatabaseField(columnName = AddressMapping.POSTAL_CODE_COLUMN_NAME)
    private String postalCode;

    @DatabaseField(columnName = AddressMapping.CITY_COLUMN_NAME)
    private String city;

    @DatabaseField(columnName = AddressMapping.COUNTRY_COLUMN_NAME)
    private String country;

    public Address() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
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
	    StringBuilder builder = new StringBuilder();
	    if ( getStreet() != null )
		    builder.append(getStreet()).append(", ");
	    if ( getPostalCode() != null ) {
		    builder.append(getPostalCode()).append(" ");
		    if ( getCity() == null )
			    builder.replace(builder.length() - 1, builder.length(), ", ");   // postal code without city
	    }
	    if ( getCity() != null )
		    builder.append(getCity()).append(", ");
	    if ( getCountry() != null)
		    builder.append(getCountry());
	    return builder.toString();
    }
}

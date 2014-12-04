package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.accounts.IndividualMapping;

import java.sql.Date;

/**
 * Created by Sławek on 12.06.14.
 */

@DatabaseTable(tableName = IndividualMapping.TABLE_NAME)
public class Individual {

    @DatabaseField(generatedId = true, index = true)
    private int id;

    @DatabaseField(columnName = IndividualMapping.FIRST_NAME_COLUMN_NAME)
    private String firstName;

    @DatabaseField(columnName =  IndividualMapping.LAST_NAME_COLUMN_NAME)
    private String lastName;

    @DatabaseField(columnName = IndividualMapping.DESCRIPTION_COLUMN_NAME)
    private String description;

    @DatabaseField(columnName = IndividualMapping.CITY_COLUMN_NAME)
    private String city;

    @DatabaseField(columnName = IndividualMapping.COUNTRY_COLUMN_NAME)
    private String country;

    public Individual() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Individual{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName" + lastName +
                ", description" + description +
                ", city" + city +
                ", country" + country +
                "}\n";
    }
}

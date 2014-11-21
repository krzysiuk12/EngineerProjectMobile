package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by Sławek on 12.06.14.
 */

@DatabaseTable(tableName = "Individuals")
public class Individual {

    @DatabaseField(generatedId = true, index = true)
    private int id;

    @DatabaseField(columnName = "firstName")
    private String firstName;

    @DatabaseField(columnName = "middleName")
    private String middleName;

    @DatabaseField(columnName = "lastName")
    private String lastName;

    @DatabaseField(columnName = "email")
    private String email;

    @DatabaseField(columnName = "dateOfBirth")
    private Date dateOfBirth;

    @DatabaseField(columnName = "gender")
    private String gender;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "facebookAccountUrl")
    private String facebookAccountUrl;

    @DatabaseField(columnName = "city")
    private String city;

    @DatabaseField(columnName = "country")
    private String country;

    public Individual() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFacebookAccountUrl() { return facebookAccountUrl; }
    public void setFacebookAccountUrl(String facebookAccountUrl) { this.facebookAccountUrl = facebookAccountUrl; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "Individual{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", middleName" + middleName +
                ", lastName" + lastName +
                ", email" + email +
                ", dateOfBirth" + dateOfBirth +
                ", gender" + gender +
                ", description" + description +
                ", facebookAccountUrl" + facebookAccountUrl +
                ", city" + city +
                ", country" + country +
                "}\n";
    }
}

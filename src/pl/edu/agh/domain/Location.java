package pl.edu.agh.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Krzysiu on 2014-06-08.
 */
@DatabaseTable(tableName = "Locations")
public class Location {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "longitude")
    private double longitude;

    @DatabaseField(columnName = "latitude")
    private double latitude;

    public Location() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                "}\n";
    }
}

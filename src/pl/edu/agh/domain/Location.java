package pl.edu.agh.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

/**
 * Created by Krzysiu on 2014-06-08.
 */
@DatabaseTable(tableName = "Locations")
public class Location {

    @DatabaseField(generatedId = true, index = true)
    private Long id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "description")
    private String description;

    @DatabaseField(columnName = "longitude")
    private double longitude;

    @DatabaseField(columnName = "latitude")
    private double latitude;

    @DatabaseField(columnName = "status")
    private String status;

    @DatabaseField(columnName = "creationDate")
    private Timestamp creationDate;

    @DatabaseField(columnName = "createdByAccountId")
    private Long createdByAccountId;

    @DatabaseField(columnName = "removedByAccountId")
    private Long removedByAccountId;

    @DatabaseField(columnName = "address", canBeNull = true, foreign = true)
    private Long addressId;

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

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {

        return status;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
    public Timestamp getCreationDate() {

        return creationDate;
    }

    public Long getCreatedByAccountId() {
        return createdByAccountId;
    }
    public void setCreatedByAccountId(Long createdByAccountId) {
        this.createdByAccountId = createdByAccountId;
    }

    public Long getRemovedByAccountId() {
        return removedByAccountId;
    }
    public void setRemovedByAccountId(Long removedByAccountId) {
        this.removedByAccountId = removedByAccountId;
    }

    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", status=" + status +
                ", createdByAccountId=" + createdByAccountId +
                ", removedByAccountId=" + removedByAccountId +
                ", addressId=" + addressId +
                "}\n";
    }


}

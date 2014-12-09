package pl.edu.agh.domain.locations;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.locations.LocationMapping;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-08.
 */
@DatabaseTable(tableName = LocationMapping.TABLE_NAME)
public class Location extends BaseObject implements Serializable {

    public enum Status implements Serializable {
        /**
         * Draft that user created, cannot be used as Location (FK), can be changed to available state.
         */
        DRAFT,
        /**
         * Currently available and send to user location that will be shown on the map.
         */
        AVAILABLE,
        /**
         * Currently unavaible but not removed from database location. Location is unavailable for some reason for some time.
         */
        UNAVAILABLE,
        /**
         * Permanently unavailable location.
         */
        REMOVED
    }

    @DatabaseField(columnName = LocationMapping.NAME_COLUMN_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = LocationMapping.DESCRIPTION_COLUMN_NAME)
    private String description;

    @DatabaseField(columnName = LocationMapping.LONGITUDE_COLUMN_NAME, canBeNull = false)
    private double longitude;

    @DatabaseField(columnName = LocationMapping.LATITUDE_COLUMN_NAME, canBeNull = false)
    private double latitude;

    @DatabaseField(columnName = LocationMapping.STATUS_COLUMN_NAME, canBeNull = false, width = LocationMapping.STATUS_DEFAULT_FIELD_WIDTH)
    private Status status;

    @DatabaseField(columnName = LocationMapping.CREATION_DATE_COLUMN_NAME, canBeNull = false)
    private Date creationDate;

    @DatabaseField(columnName = LocationMapping.CREATED_BY_ACCOUNT_COLUMN_NAME, canBeNull = true, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private UserAccount createdByAccount;

    @DatabaseField(columnName = LocationMapping.ADDRESS_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Address address;

    @DatabaseField(columnName = LocationMapping.URL_COLUMN_NAME)
    private String url;

    @DatabaseField(columnName = LocationMapping.RATING_COLUMN_NAME)
    private double rating;

    @DatabaseField(columnName = LocationMapping.USERS_PRIVATE_COLUMN_NAME)
    private boolean usersPrivate;

	@ForeignCollectionField
	private Collection<Comment> comments;

    @DatabaseField(columnName = LocationMapping.SYNCED_COLUMN_NAME, canBeNull = true)
    private boolean isSynced;

    public Location() {
        this.isSynced = true;   // default value
    }

// <editor-fold desc="Geters and Setters">

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

    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public UserAccount getCreatedByAccount() {
        return createdByAccount;
    }
    public void setCreatedByAccount(UserAccount createdByAccount) {
        this.createdByAccount = createdByAccount;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isUsersPrivate() {
        return usersPrivate;
    }
    public void setUsersPrivate(boolean usersPrivate) {
        this.usersPrivate = usersPrivate;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }

    public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	// </editor-fold>

    @Override
    public String toString() {
        return "Location{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", status=" + status +
                ", createdByAccount=" + (createdByAccount != null ? createdByAccount.toString() : "N/A") +
                ", address=" + address.toString() +
                ", isSynced=" + isSynced +
                "}\n";
    }

}

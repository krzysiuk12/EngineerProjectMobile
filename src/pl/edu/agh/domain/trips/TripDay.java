package pl.edu.agh.domain.trips;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripDayMapping;
import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripDayMapping.TABLE_NAME)
public class TripDay extends BaseObject {

    @DatabaseField(columnName = TripDayMapping.TRIP_COLUMN_NAME, foreign = true, canBeNull = false)
    private Trip trip;

    @ForeignCollectionField(eager = true, foreignFieldName = "tripDay")
    private Collection<TripDayLocation> locations;

    @ForeignCollectionField(eager = false, foreignFieldName = "tripDay")
    private Collection<TripStep> tripSteps;

    @DatabaseField(columnName = TripDayMapping.DATE_COLUMN_NAME, dataType = DataType.DATE, canBeNull = false)
    private Date date;

    public TripDay() {
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Collection<TripDayLocation> getLocations() {
        return locations;
    }

    public void setLocations(Collection<TripDayLocation> locations) {
        this.locations = locations;
    }

    public Collection<TripStep> getTripSteps() {
        return tripSteps;
    }

    public void setTripSteps(Collection<TripStep> tripSteps) {
        this.tripSteps = tripSteps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TripDay{id=" +
                getId() + ", date=" +
                getDate() + "}";
    }

}

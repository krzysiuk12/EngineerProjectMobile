package pl.edu.agh.domain.trips;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripDayLocationMapping;
import pl.edu.agh.dbmodel.trips.TripDayMapping;
import pl.edu.agh.dbmodel.trips.TripStepMapping;
import pl.edu.agh.domain.common.BaseObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripDayMapping.TABLE_NAME)
public class TripDay extends BaseObject {

    @DatabaseField(columnName = TripDayMapping.TRIP_COLUMN_NAME, foreign = true, canBeNull = false)
    private Trip trip;

    @ForeignCollectionField(eager = false, foreignFieldName = "tripDay")
    private Collection<TripDayLocation> locations;

    @ForeignCollectionField(eager = false, foreignFieldName = "tripDay")
    private Collection<TripStep> tripSteps;

    @DatabaseField(columnName = TripDayMapping.DATE_COLUMN_NAME, dataType = DataType.DATE, canBeNull = false)
    private Date date;

    @DatabaseField(columnName = TripDayMapping.PLANNED_START_TIME_COLUMN_NAME, dataType = DataType.DATE_TIME, canBeNull = false)
    private Date plannedStartTime;

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

    public Date getPlannedStartTime() {
        return plannedStartTime;
    }

    public void setPlannedStartTime(Date plannedStartTime) {
        this.plannedStartTime = plannedStartTime;
    }

}

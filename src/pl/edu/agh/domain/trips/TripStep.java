package pl.edu.agh.domain.trips;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripStepMapping;
import pl.edu.agh.domain.common.BaseObject;
import pl.edu.agh.domain.locations.Location;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripStepMapping.TABLE_NAME)
public class TripStep extends BaseObject {

    @DatabaseField(columnName = TripStepMapping.TRIP_DAY_COLUMN_NAME, foreign = true, canBeNull = false)
    private TripDay tripDay;

    @DatabaseField(columnName = TripStepMapping.START_LOCATION_COLUMN_NAME, foreign = true, canBeNull = false)
    private Location startLocation;

    @DatabaseField(columnName = TripStepMapping.END_LOCATION_COLUMN_NAME, foreign = true, canBeNull = false)
    private Location endLocation;

    @DatabaseField(columnName = TripStepMapping.DISTANCE_COLUMN_NAME, width = TripStepMapping.DISTANCE_DEFAULT_FIELD_WIDTH, canBeNull = false)
    private String distance;

    @DatabaseField(columnName = TripStepMapping.DISTANCE_UNIT_COLUMN_NAME, canBeNull = false)
    private DistanceUnit distanceUnit;

    @DatabaseField(columnName = TripStepMapping.DURATION_COLUMN_NAME, width = TripStepMapping.DURATION_DEFAULT_FIELD_WIDTH, canBeNull = false)
    private String duration;

    @ForeignCollectionField(eager = false, foreignFieldName = "tripStep")
    private Collection<TripDirection> directions;

    public TripStep() {
    }

    public TripDay getTripDay() {
        return tripDay;
    }

    public void setTripDay(TripDay tripDay) {
        this.tripDay = tripDay;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Collection<TripDirection> getDirections() {
        return directions;
    }

    public void setDirections(Collection<TripDirection> directions) {
        this.directions = directions;
    }
}

package pl.edu.agh.domain.trips;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripDirectionMapping;
import pl.edu.agh.domain.common.BaseObject;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripDirectionMapping.TABLE_NAME)
public class TripDirection extends BaseObject  {

    @DatabaseField(columnName = TripDirectionMapping.ORDINAL_COLUMN_NAME, canBeNull = false)
    private int ordinal;

    @DatabaseField(columnName = TripDirectionMapping.TRIP_STEP_COLUMN_NAME, foreign = true, canBeNull = false)
    private TripStep tripStep;

    @DatabaseField(columnName = TripDirectionMapping.DISTANCE_COLUMN_NAME)
    private String distance;

    @DatabaseField(columnName = TripDirectionMapping.DURATION_COLUMN_NAME)
    private String duration;

    @DatabaseField(columnName = TripDirectionMapping.START_COORDINATE_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Coordinate startCoordinate;

    @DatabaseField(columnName = TripDirectionMapping.END_COORDINATE_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Coordinate endCoordinate;

    @DatabaseField(columnName = TripDirectionMapping.INSTRUCTION_COLUMN_NAME, canBeNull = false)
    private String instruction;

    @DatabaseField(columnName = TripDirectionMapping.TRAVEL_MODE_COLUMN_NAME)
    private TravelMode travelMode;

    public TripDirection() {
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public TripStep getTripStep() {
        return tripStep;
    }

    public void setTripStep(TripStep tripStep) {
        this.tripStep = tripStep;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(Coordinate startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }
}

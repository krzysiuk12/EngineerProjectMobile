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

    @DatabaseField(columnName = TripDirectionMapping.START_LATITUDE_COLUMN_NAME, canBeNull = false)
    private double startLatitude;

    @DatabaseField(columnName = TripDirectionMapping.START_LONGITUDE_COLUMN_NAME, canBeNull = false)
    private double startLongitude;

    @DatabaseField(columnName = TripDirectionMapping.END_LATITUDE_COLUMN_NAME, canBeNull = false)
    private double endLatitude;

    @DatabaseField(columnName = TripDirectionMapping.END_LONGITUDE_COLUMN_NAME, canBeNull = false)
    private double endLongitude;

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

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
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

package pl.edu.agh.domain.trips;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripDayLocationMapping;
import pl.edu.agh.domain.common.BaseObject;
import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripDayLocationMapping.TABLE_NAME)
public class TripDayLocation extends BaseObject {

    @DatabaseField(columnName = TripDayLocationMapping.TRIP_COLUMN_NAME, foreign = true, canBeNull = false)
    private TripDay tripDay;

    @DatabaseField(columnName = TripDayLocationMapping.LOCATION_COLUMN_NAME, foreign = true, canBeNull = false)
    private Location location;

    @DatabaseField(columnName = TripDayLocationMapping.ORDINAL_COLUMN_NAME, canBeNull = false)
    private int ordinal;

    public TripDayLocation() {
    }

    public TripDay getTripDay() {
        return tripDay;
    }

    public void setTripDay(TripDay tripDay) {
        this.tripDay = tripDay;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
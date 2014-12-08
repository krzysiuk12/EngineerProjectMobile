package pl.edu.agh.domain.trips;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.CoordinateMapping;

/**
 * Created by Magda on 2014-12-03.
 */
@DatabaseTable(tableName = CoordinateMapping.TABLE_NAME)
public class Coordinate {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = CoordinateMapping.LATITUDE_COLUMN_NAME, canBeNull = false)
	private double latitude;

	@DatabaseField(columnName = CoordinateMapping.LONGITUDE_COLUMN_NAME, canBeNull = false)
	private double longitude;

	public Coordinate() {
	}

	public Coordinate(double lat, double lng) {
		this.latitude = lat;
		this.longitude = lng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Coordinate{" + latitude + ", " + longitude + "}";
	}
}

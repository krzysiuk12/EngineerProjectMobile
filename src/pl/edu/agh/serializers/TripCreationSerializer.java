package pl.edu.agh.serializers;

import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;

import java.util.Date;
import java.util.List;
/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
public class TripCreationSerializer {

	private String name;
	private String description;
	private Date startDate;
	private TravelMode travelMode;
	private DistanceUnit distanceUnit;
	private List<TripDayCreationSerializer> tripDays;

	public TripCreationSerializer() {
	}

	public TripCreationSerializer(String name, String description, Date startDate, TravelMode travelMode, DistanceUnit distanceUnit, List<TripDayCreationSerializer> tripDays) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.travelMode = travelMode;
		this.distanceUnit = distanceUnit;
		this.tripDays = tripDays;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public TravelMode getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(TravelMode travelMode) {
		this.travelMode = travelMode;
	}

	public DistanceUnit getDistanceUnit() {
		return distanceUnit;
	}

	public void setDistanceUnit(DistanceUnit distanceUnit) {
		this.distanceUnit = distanceUnit;
	}

	public List<TripDayCreationSerializer> getTripDays() {
		return tripDays;
	}

	public void setTripDays(List<TripDayCreationSerializer> tripDays) {
		this.tripDays = tripDays;
	}

	@Override
	public String toString() {
		return "TripCreationSerializer["
				+ "name=" + name + ", "
				+ "desc=" + description + ", "
				+ "startDate=" + startDate + ", "
				+ "distanceUnit=" + distanceUnit + ", "
				+ "travelMode=" + travelMode + ", "
				+ "days=" + tripDays + "]";
	}
}

package pl.edu.agh.serializers;


import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysztof Kicinger on 2014-11-17.
 */
public class LocationStatusChangeSerializer {

	private Location.Status previousStatus;
	private Location.Status currentStatus;

	public LocationStatusChangeSerializer() {
	}

	public LocationStatusChangeSerializer(Location.Status previousStatus, Location.Status currentStatus) {
		this.previousStatus = previousStatus;
		this.currentStatus = currentStatus;
	}

	public Location.Status getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(Location.Status previousStatus) {
		this.previousStatus = previousStatus;
	}

	public Location.Status getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Location.Status currentStatus) {
		this.currentStatus = currentStatus;
	}
}

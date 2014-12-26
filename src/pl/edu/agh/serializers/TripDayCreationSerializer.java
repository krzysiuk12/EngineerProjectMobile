package pl.edu.agh.serializers;

import java.util.List;

/**
 * Created by Magda on 2014-12-24.
 */
public class TripDayCreationSerializer {

	private long originLocationId;
	private long destinationLocationId;
	private List<Long> waypointLocationIds;

	public TripDayCreationSerializer() {
	}

	public TripDayCreationSerializer(long originLocationId, long destinationLocationId, List<Long> waypointLocationIds) {
		this.originLocationId = originLocationId;
		this.destinationLocationId = destinationLocationId;
		this.waypointLocationIds = waypointLocationIds;
	}

	public long getOriginLocationId() {
		return originLocationId;
	}

	public void setOriginLocationId(long originLocationId) {
		this.originLocationId = originLocationId;
	}

	public long getDestinationLocationId() {
		return destinationLocationId;
	}

	public void setDestinationLocationId(long destinationLocationId) {
		this.destinationLocationId = destinationLocationId;
	}

	public List<Long> getWaypointLocationIds() {
		return waypointLocationIds;
	}

	public void setWaypointLocationIds(List<Long> waypointLocationIds) {
		this.waypointLocationIds = waypointLocationIds;
	}

}

package pl.edu.agh.serializers;

import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.util.ArrayList;
import java.util.Collections;
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

	public TripDayCreationSerializer(List<TripDayLocation> tripDayLocations) {
		Collections.sort(tripDayLocations);
		if ( !tripDayLocations.isEmpty() ) {
			this.originLocationId = tripDayLocations.remove(0).getLocation().getGlobalId();
		}
		if ( !tripDayLocations.isEmpty() ) {
			this.destinationLocationId = tripDayLocations.remove(tripDayLocations.size() - 1).getLocation().getGlobalId();
		}

		this.waypointLocationIds = new ArrayList<>();
		for ( TripDayLocation location : tripDayLocations ) {
			this.waypointLocationIds.add(location.getLocation().getGlobalId());
		}
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

	@Override
	public String toString() {
		return "TripDayCreationSerializer["
				+ originLocationId + ", "
				+ waypointLocationIds + ", "
				+ destinationLocationId + "]";
	}
}

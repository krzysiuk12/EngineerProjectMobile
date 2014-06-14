package pl.edu.agh.services.implementation;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.Location;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class GoogleMapsManagementService {

    public MarkerOptions createLocationMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(location.getName());
        markerOptions.position(latLng);
        markerOptions.snippet(location.getStatus());
        return markerOptions;
    }

    public LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

}

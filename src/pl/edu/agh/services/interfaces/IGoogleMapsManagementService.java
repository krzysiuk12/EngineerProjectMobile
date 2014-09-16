package pl.edu.agh.services.interfaces;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysiu on 2014-09-16.
 */
public interface IGoogleMapsManagementService {

    public MarkerOptions createLocationMarker(Location location);

    public LatLng getLatLngFromLocation(Location location);

    public void setNormalMapType(GoogleMap map);

    public void setTerrainMapType(GoogleMap map);

    public void setSatelliteMapType(GoogleMap map);

    public void setHybridMapType(GoogleMap map);

    public void setMyLocationEnabled(GoogleMap map);

    public void setMapPosition(GoogleMap map, LatLng latlng);

    public void setMapPosition(GoogleMap map, LatLng latlng, int zoom);

    public LatLng getMyLocation(GoogleMap map);
}

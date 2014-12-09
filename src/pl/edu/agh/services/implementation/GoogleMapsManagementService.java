package pl.edu.agh.services.implementation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;


/**
 * Created by Krzysiu on 2014-06-14.
 */
public class GoogleMapsManagementService extends BaseService implements IGoogleMapsManagementService {

    public static final double MINIMUM_STAR_RATING = 8.0;

    private enum MarkerColor {
        BLUE,
        GREEN,
        ORANGE,
        RED
    }

    @Override
    public MarkerOptions createLocationMarker(Location location) {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(location.getName());
            markerOptions.position(latLng);
            markerOptions.snippet(location.getStatus().name());
            setMarkerIcon(markerOptions, location);
            return markerOptions;
        }
        return null;
    }

    @Override
    public MarkerOptions createNewMarker(LatLng position) {
        if ( position != null ) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            return markerOptions;
        }
        return null;
    }

    @Override
    public LatLng getLatLngFromLocation(Location location) {
        if (location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    @Override
    public void setNormalMapType(GoogleMap map) {
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    @Override
    public void setTerrainMapType(GoogleMap map) {
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
    }

    @Override
    public void setSatelliteMapType(GoogleMap map) {
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

    @Override
    public void setHybridMapType(GoogleMap map) {
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
    }

    @Override
    public void setMyLocationEnabled(GoogleMap map) {
        if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void setMapPosition(GoogleMap map, LatLng latLng) {
        if (map != null && latLng != null) {
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void setMapPosition(GoogleMap map, LatLng latLng, int zoom) {
        if (map != null && latLng != null && zoom > 0) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }
    }

    @Override
    public LatLng getMyLocation(GoogleMap map) {
        if (map != null && map.getMyLocation() != null) {
            return new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
        }
        return null;
    }

    private void setMarkerIcon(MarkerOptions option, Location location) {
        if (location.isUsersPrivate()) {
            setPrivateMarkerIcon(option, location, MarkerColor.BLUE);
        } else {
            if (location.getRating() >= MINIMUM_STAR_RATING) {
                setStarMarkerIcon(option, location, MarkerColor.ORANGE);
            } else {
                switch (location.getStatus()) {
                    case DRAFT:
                        setDraftMarkerIcon(option, location, MarkerColor.BLUE);
                        break;
                    case AVAILABLE:
                        setSimpleMarkerIcon(option, location, MarkerColor.GREEN);
                        break;
                    case UNAVAILABLE:
                        setUnavailableMarkerIcon(option, location, MarkerColor.RED);
                        break;
                    case REMOVED:
                        break;
                }
            }
        }
    }

    private void setStarMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch (color) {
            case BLUE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_blue));
                break;
            case GREEN:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_green));
                break;
            case ORANGE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_orange));
                break;
            case RED:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_red));
                break;
        }
    }

    private void setUnavailableMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch (color) {
            case BLUE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_blue));
                break;
            case GREEN:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_green));
                break;
            case ORANGE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_orange));
                break;
            case RED:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_red));
                break;
        }
    }

    private void setPrivateMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch (color) {
            case BLUE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_blue));
                break;
            case GREEN:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_green));
                break;
            case ORANGE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_orange));
                break;
            case RED:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_red));
                break;
        }
    }

    private void setDraftMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch (color) {
            case BLUE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_blue));
                break;
            case GREEN:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_green));
                break;
            case ORANGE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_orange));
                break;
            case RED:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_red));
                break;
        }
    }

    private void setSimpleMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch (color) {
            case BLUE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_blue));
                break;
            case GREEN:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_green));
                break;
            case ORANGE:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_orange));
                break;
            case RED:
                option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_red));
                break;
        }
    }

}

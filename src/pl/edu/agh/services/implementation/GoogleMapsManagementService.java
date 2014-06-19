package pl.edu.agh.services.implementation;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class GoogleMapsManagementService {

    private enum MarkerColor {
        BLUE,
        GREEN,
        ORANGE,
        RED
    }

    public MarkerOptions createLocationMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(location.getName());
        markerOptions.position(latLng);
        markerOptions.snippet(location.getStatus().name());
        setMarkerIcon(markerOptions, location);
        return markerOptions;
    }

    public LatLng getLatLngFromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    private void setMarkerIcon(MarkerOptions option, Location location) {
        if(location.isUsersPrivate()) {
            setPrivateMarkerIcon(option, location, MarkerColor.BLUE);
        } else {
            if(location.getRating() >= Location.MINIMUM_STAR_RATING) {
                setStarMarkerIcon(option, location, MarkerColor.ORANGE);
            } else {
                switch(location.getStatus()) {
                    case DRAFT: setDraftMarkerIcon(option, location, MarkerColor.BLUE); break;
                    case AVAILABLE: setSimpleMarkerIcon(option, location, MarkerColor.GREEN); break;
                    case UNAVAILABLE: setUnavailableMarkerIcon(option, location, MarkerColor.RED); break;
                    case REMOVED: break;
                }
            }
        }
    }

    private void setStarMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch(color) {
            case BLUE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_blue)); break;
            case GREEN: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_green)); break;
            case ORANGE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_orange)); break;
            case RED: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_star_red)); break;
        }
    }

    private void setUnavailableMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch(color) {
            case BLUE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_blue)); break;
            case GREEN: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_green)); break;
            case ORANGE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_orange)); break;
            case RED: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_x_red)); break;
        }
    }

    private void setPrivateMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch(color) {
            case BLUE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_blue)); break;
            case GREEN: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_green)); break;
            case ORANGE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_orange)); break;
            case RED: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_exclamation_mark_red)); break;
        }
    }

    private void setDraftMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch(color) {
            case BLUE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_blue)); break;
            case GREEN: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_green)); break;
            case ORANGE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_orange)); break;
            case RED: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_question_mark_red)); break;
        }
    }

    private void setSimpleMarkerIcon(MarkerOptions option, Location location, MarkerColor color) {
        switch(color) {
            case BLUE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_blue)); break;
            case GREEN: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_green)); break;
            case ORANGE: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_orange)); break;
            case RED: option.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_empty_red)); break;
        }
    }
}

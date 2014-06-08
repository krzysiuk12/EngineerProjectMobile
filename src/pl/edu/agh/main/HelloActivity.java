package pl.edu.agh.main;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class HelloActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        System.out.println("TEST");

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ShowLocationsOnMapView_Map)).getMap();
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(15.0, 15.0), 16));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}

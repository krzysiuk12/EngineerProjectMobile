package pl.edu.agh.main;

import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.Location;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.repositories.interfaces.ILocationRepository;

public class HelloActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {
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

        Location location = new Location();
        location.setDescription("Descritption");
        location.setName("Name");
        location.setLatitude(123.123);
        location.setLongitude(123.123);

        ILocationRepository locationRepository = new OrmLiteLocationRepository(getHelper());
        locationRepository.saveLocation(location);

        ((TextView)findViewById(R.id.HelloActivity_TextView)).setText(locationRepository.getAllLocations().toString());

    }
}

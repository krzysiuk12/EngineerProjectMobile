package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.repositories.interfaces.ILocationRepository;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class OrmLiteLocationRepository implements ILocationRepository {

    public OrmLiteSqliteOpenHelper openHelper;

    public OrmLiteLocationRepository(OrmLiteSqliteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    @Override
    public void saveLocation(Location location) {
        ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().create(location);
    }

/*    @Override
    public List<Location> getAllLocations() {
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForAll();
    }*/

    @Override
    public Location getLocationById(Long id) throws LocationException {
        return null;
    }

    @Override
    public Location getLocationByName(String name) throws LocationException {
        return null;
    }

    @Override
    public Location getLocationByCoordinates(double longitude, double latitude) throws LocationException {
        return null;
    }

    @Override
    public List<Location> getAllUserLocations(UserAccount account) throws LocationException {
        return null;
    }

    @Override
    public List<Location> getAllUserPrivateLocations(UserAccount account) throws LocationException {
        return null;
    }
}

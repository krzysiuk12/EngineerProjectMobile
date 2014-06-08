package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.Location;
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

    @Override
    public List<Location> getAllLocations() {
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForAll();
    }
}

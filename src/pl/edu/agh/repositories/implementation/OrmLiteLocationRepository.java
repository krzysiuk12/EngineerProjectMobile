package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.QueryBuilder;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.dbmodel.accounts.UserAccountMapping;
import pl.edu.agh.dbmodel.common.BaseObjectMapping;
import pl.edu.agh.dbmodel.locations.LocationMapping;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.repositories.interfaces.ILocationRepository;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class OrmLiteLocationRepository implements ILocationRepository {

    public OrmLiteSqliteOpenHelper openHelper;

    private static final double EPSILON = 0.001;

    public OrmLiteLocationRepository(OrmLiteSqliteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    @Override
    public int saveLocation(Location location) {
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().create(location);
    }

	@Override
	public void saveOrUpdateLocation(Location location) {
		((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().createOrUpdate(location);
	}

	@Override
	public void updateLocation(Location location) {
		((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().update(location);
	}

    @Override
    public List<Location> getAllLocations() {
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForAll();
    }

    @Override
    public List<Location> getAllPublicLocations() throws LocationException {
        return ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryForEq(LocationMapping.USERS_PRIVATE_COLUMN_NAME, false);
    }

    @Override
    public Location getLocationById(Long id) throws LocationException {
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForId(id);
    }

    @Override
    public Location getLocationByGlobalId(Long id) throws LocationException {
        List<Location> locations = ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryForEq(BaseObjectMapping.GLOBAL_ID_COLUMN_NAME, id);
        if ( !locations.isEmpty() ) {
            return locations.get(0);
        }
        return null;
    }

    @Override
    public Location getLocationByName(String name) throws LocationException {
        List<Location> results = ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForEq(LocationMapping.NAME_COLUMN_NAME, name);
	    return results.size() > 0 ? results.get(0) : null;
    }

    @Override
    public Location getLocationByCoordinates(double longitude, double latitude) throws LocationException {
        QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryBuilder();
        try {
            queryBuilder.where().between(LocationMapping.LATITUDE_COLUMN_NAME, latitude - EPSILON, latitude + EPSILON);
            queryBuilder.where().between(LocationMapping.LONGITUDE_COLUMN_NAME, longitude - EPSILON, longitude + EPSILON);
            return ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryForFirst(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> getAllUserLocations(UserAccount account) throws LocationException {
        QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryBuilder();
        try {
            queryBuilder.where().eq(LocationMapping.CREATED_BY_ACCOUNT_COLUMN_NAME, account);
            return ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> getAllUserPrivateLocations(String token) throws LocationException {
        QueryBuilder userAccountQueryBuilder = ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryBuilder();
        QueryBuilder locationQueryBuilder = ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryBuilder();
        try {
            userAccountQueryBuilder.where().eq(UserAccountMapping.TOKEN_COLUMN_NAME, UserAccountManagementService.getToken());
            locationQueryBuilder.where().eq(LocationMapping.USERS_PRIVATE_COLUMN_NAME, true);
            return locationQueryBuilder.join(userAccountQueryBuilder).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> getAllUserPrivateLocations(UserAccount account) throws LocationException {
        QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().queryBuilder();
        try {
            queryBuilder.where().eq(LocationMapping.CREATED_BY_ACCOUNT_COLUMN_NAME, account);
            queryBuilder.where().eq(LocationMapping.USERS_PRIVATE_COLUMN_NAME, true);
            return ((TestDatabaseHelper) openHelper).getLocationsRuntimeExceptionDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Location> getAllNewPublicLocations() throws LocationException {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(LocationMapping.SYNCED_COLUMN_NAME, false);
        fieldValues.put(LocationMapping.USERS_PRIVATE_COLUMN_NAME, false);
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForFieldValues(fieldValues);
    }

    @Override
    public List<Location> getAllNewPrivateLocations() throws LocationException {
        Map<String, Object> fieldValues = new HashMap<String, Object>();
        fieldValues.put(LocationMapping.SYNCED_COLUMN_NAME, false);
        fieldValues.put(LocationMapping.USERS_PRIVATE_COLUMN_NAME, true);
        return ((TestDatabaseHelper)openHelper).getLocationsRuntimeExceptionDao().queryForFieldValues(fieldValues);
    }
}

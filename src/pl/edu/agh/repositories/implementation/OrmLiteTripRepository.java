package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.stmt.QueryBuilder;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.dbmodel.trips.TripMapping;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.repositories.interfaces.ITripRepository;
import pl.edu.agh.tools.StringTools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public class OrmLiteTripRepository implements ITripRepository {

	public OrmLiteSqliteOpenHelper openHelper;

	public OrmLiteTripRepository(OrmLiteSqliteOpenHelper openHelper) {
		this.openHelper = openHelper;
	}

	@Override
	public void saveTrip(Trip trip) throws TripException {
		((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().create(trip);
	}

	@Override
	public List<Trip> getAllTrips() throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForAll();
	}

	@Override
	public List<Trip> getPastTrips() throws TripException {
		QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryBuilder();
		try {
			queryBuilder.where().lt(TripMapping.END_DATE_COLUMN_NAME, new Date());
			return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trip> getCurrentTrips() throws TripException {
		QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryBuilder();
		try {
			Date currentDate = new Date();
			queryBuilder.where().lt(TripMapping.START_DATE_COLUMN_NAME, currentDate);
			queryBuilder.where().gt(TripMapping.END_DATE_COLUMN_NAME, currentDate);
			return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trip> getFutureTrips() throws TripException {
		QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryBuilder();
		try {
			queryBuilder.where().gt(TripMapping.START_DATE_COLUMN_NAME, new Date());
			return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Trip getTripById(long id) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForId(id);
	}

	@Override
	public Trip getTripByName(String name) throws TripException {
		List<Trip> results = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForEq(TripMapping.NAME_COLUMN_NAME, name);
		return results.size() > 0 ? results.get(0) : null;
	}

}

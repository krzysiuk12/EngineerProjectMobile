package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.dbmodel.accounts.UserAccountMapping;
import pl.edu.agh.dbmodel.common.BaseObjectMapping;
import pl.edu.agh.dbmodel.trips.TripDayLocationMapping;
import pl.edu.agh.dbmodel.trips.TripDayMapping;
import pl.edu.agh.dbmodel.trips.TripDirectionMapping;
import pl.edu.agh.dbmodel.trips.TripMapping;
import pl.edu.agh.dbmodel.trips.TripStepMapping;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.domain.trips.TripStep;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.repositories.interfaces.ITripRepository;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.utils.TimeUtils;

import java.sql.SQLException;
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
		((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().createOrUpdate(trip);
	}
	@Override
	public void saveTripDay(TripDay tripDay) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDayRuntimeExceptionDao().createOrUpdate(tripDay);
	}

	@Override
	public void saveTripDayLocation(TripDayLocation tripDayLocation) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDayLocationRuntimeExceptionDao().createOrUpdate(tripDayLocation);
	}

	@Override
	public void saveTripStep(TripStep tripStep) throws TripException {
		((TestDatabaseHelper) openHelper).getTripStepRuntimeExceptionDao().createOrUpdate(tripStep);
	}

	@Override
	public void saveTripDirection(TripDirection tripDirection) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDirectionRuntimeExceptionDao().createOrUpdate(tripDirection);
	}

	@Override
	public void updateTrip(Trip trip) throws TripException {
		((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().update(trip);
	}

	@Override
	public void updateTripDay(TripDay tripDay) throws TripException {
		int result = ((TestDatabaseHelper) openHelper).getTripDayRuntimeExceptionDao().update(tripDay);
	}

	@Override
	public void updateTripDayLocation(TripDayLocation tripDayLocation) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDayLocationRuntimeExceptionDao().update(tripDayLocation);
	}

	@Override
	public void updateTripStep(TripStep tripStep) throws TripException {
		((TestDatabaseHelper) openHelper).getTripStepRuntimeExceptionDao().update(tripStep);
	}

	@Override
	public void updateTripDirection(TripDirection tripDirection) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDirectionRuntimeExceptionDao().update(tripDirection);
	}

	@Override
	public void deleteTrip(Trip trip) throws TripException {
		((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().delete(trip);
	}

	@Override
	public void deleteTripDay(TripDay tripDay) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDayRuntimeExceptionDao().delete(tripDay);
	}

	@Override
	public void deleteTripDayLocation(TripDayLocation tripDayLocation) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDayLocationRuntimeExceptionDao().delete(tripDayLocation);
	}

	@Override
	public void deleteTripStep(TripStep tripStep) throws TripException {
		((TestDatabaseHelper) openHelper).getTripStepRuntimeExceptionDao().delete(tripStep);
	}

	@Override
	public void deleteTripDirection(TripDirection tripDirection) throws TripException {
		((TestDatabaseHelper) openHelper).getTripDirectionRuntimeExceptionDao().delete(tripDirection);
	}

	@Override
	public List<Trip> getAllTrips() throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForAll();
	}

	@Override
	public List<Trip> getPastTrips() throws TripException {
		QueryBuilder queryBuilder = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryBuilder();
		try {
			Date currentDate = TimeUtils.formatDateForDatabase(new Date());
			queryBuilder.where().lt(TripMapping.END_DATE_COLUMN_NAME, currentDate);
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
			Date currentDate = TimeUtils.formatDateForDatabase(new Date());

			Where where = queryBuilder.where();
			where.le(TripMapping.START_DATE_COLUMN_NAME, currentDate);
			where.and();
			where.ge(TripMapping.END_DATE_COLUMN_NAME, currentDate);
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
			Date currentDate = TimeUtils.formatDateForDatabase(new Date());
			queryBuilder.where().gt(TripMapping.START_DATE_COLUMN_NAME, currentDate);
			return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().query(queryBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trip> getNewUserTrips(String token) throws TripException {
		QueryBuilder tripQueryBuilder = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryBuilder();
		QueryBuilder userQueryBuilder = ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryBuilder();
		try {
			tripQueryBuilder.where().eq(TripMapping.IS_SYNCED_COLUMN_NAME, false);
			userQueryBuilder.where().eq(UserAccountMapping.TOKEN_COLUMN_NAME, token);
			return tripQueryBuilder.join(userQueryBuilder).query();
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
	public Trip getTripByGlobalId(long id) throws TripException {
		List<Trip> trips = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForEq(BaseObjectMapping.GLOBAL_ID_COLUMN_NAME, id);
		if ( !trips.isEmpty() ) {
			return trips.get(0);
		}
		return null;
	}

	@Override
	public Trip getTripByName(String name) throws TripException {
		List<Trip> results = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForEq(TripMapping.NAME_COLUMN_NAME, name);
		return results.size() > 0 ? results.get(0) : null;
	}

	@Override
	public List<TripDirection> getTripDirections(TripStep step) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripDirectionRuntimeExceptionDao().queryForEq(TripDirectionMapping.TRIP_STEP_COLUMN_NAME, step);
	}

	@Override
	public List<TripStep> getTripSteps(TripDay tripDay) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripStepRuntimeExceptionDao().queryForEq(TripStepMapping.TRIP_DAY_COLUMN_NAME, tripDay);
	}

	@Override
	public List<TripDayLocation> getTripDayLocations(TripDay tripDay) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripDayLocationRuntimeExceptionDao().queryForEq(TripDayLocationMapping.TRIP_DAY_COLUMN_NAME, tripDay);
	}

	@Override
	public List<TripDay> getTripDays(Trip trip) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripDayRuntimeExceptionDao().queryForEq(TripDayMapping.TRIP_COLUMN_NAME, trip);
	}
}

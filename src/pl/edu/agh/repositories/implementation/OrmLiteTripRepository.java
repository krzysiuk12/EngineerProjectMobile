package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.dbmodel.trips.TripMapping;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.exceptions.TripException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.repositories.interfaces.ITripRepository;
import pl.edu.agh.tools.StringTools;

import java.util.ArrayList;
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
	public Trip getTripById(long id) throws TripException {
		return ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForId(id);
	}

	@Override
	public Trip getTripByName(String name) throws TripException {
		List<Trip> results = ((TestDatabaseHelper) openHelper).getTripRuntimeExceptionDao().queryForEq(TripMapping.NAME_COLUMN_NAME, name);
		return results.size() > 0 ? results.get(0) : null;
	}

}

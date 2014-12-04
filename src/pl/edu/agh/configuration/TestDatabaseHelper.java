package pl.edu.agh.configuration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.Individual;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.accounts.UserAccountStatusEvent;
import pl.edu.agh.domain.locations.Comment;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.*;
import pl.edu.agh.services.implementation.AndroidLogService;
import pl.edu.agh.services.interfaces.ILogService;

import java.sql.SQLException;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class TestDatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "Test.db";
    private static final int DATABASE_VERSION = 1;

    private static final ILogService logService = new AndroidLogService();
    private static final String LOGGER_TAG = TestDatabaseHelper.class.getName();

    private Dao<Location, Long> locationsDao;
    private RuntimeExceptionDao<Location, Long> locationsRuntimeExceptionDao;
    private Dao<Trip, Long> tripDao;
    private RuntimeExceptionDao<Trip, Long> tripRuntimeExceptionDao;

    public TestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(LOGGER_TAG, "Test Database - onCreate Method.");
            TableUtils.createTable(connectionSource, Individual.class);
            TableUtils.createTable(connectionSource, UserAccount.class);
            TableUtils.createTable(connectionSource, UserAccountStatusEvent.class);
            TableUtils.createTable(connectionSource, Address.class);
            TableUtils.createTable(connectionSource, Location.class);
            TableUtils.createTable(connectionSource, TripDirection.class);
            TableUtils.createTable(connectionSource, TripStep.class);
            TableUtils.createTable(connectionSource, TripDayLocation.class);
            TableUtils.createTable(connectionSource, TripDay.class);
            TableUtils.createTable(connectionSource, Trip.class);
            TableUtils.createTable(connectionSource, Comment.class);
        } catch(SQLException ex) {
            Log.e(LOGGER_TAG, "Cannot create database", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(LOGGER_TAG, "Test Database - onUpgrade Method.");
            TableUtils.dropTable(connectionSource, Trip.class, true);
            TableUtils.dropTable(connectionSource, TripDay.class, true);
            TableUtils.dropTable(connectionSource, TripDayLocation.class, true);
            TableUtils.dropTable(connectionSource, TripStep.class, true);
            TableUtils.dropTable(connectionSource, TripDirection.class, true);
            TableUtils.dropTable(connectionSource, Location.class, true);
            TableUtils.dropTable(connectionSource, Address.class, true);
            TableUtils.dropTable(connectionSource, UserAccountStatusEvent.class, true);
            TableUtils.dropTable(connectionSource, UserAccount.class, true);
            TableUtils.dropTable(connectionSource, Individual.class, true);
            TableUtils.dropTable(connectionSource, Comment.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch(SQLException ex) {
            Log.e(LOGGER_TAG, "Cannot upgrade database", ex);
            throw new RuntimeException(ex);
        }
    }

    public Dao<Location, Long> getLocationsDao() throws SQLException {
        if(locationsDao == null) {
            locationsDao = getDao(Location.class);
        }
        return locationsDao;
    }

    public RuntimeExceptionDao<Location, Long> getLocationsRuntimeExceptionDao() {
        if(locationsRuntimeExceptionDao == null) {
            locationsRuntimeExceptionDao = getRuntimeExceptionDao(Location.class);
        }
        return locationsRuntimeExceptionDao;
    }

    public Dao<Trip, Long> getTripDao() throws SQLException {
        if ( tripDao == null ) {
            tripDao = getDao(Trip.class);
        }
        return tripDao;
    }

    public RuntimeExceptionDao<Trip, Long> getTripRuntimeExceptionDao() {
        if ( tripRuntimeExceptionDao == null ) {
            tripRuntimeExceptionDao = getRuntimeExceptionDao(Trip.class);
        }
        return tripRuntimeExceptionDao;
    }

    @Override
    public void close() {
        super.close();
        locationsDao = null;
        locationsRuntimeExceptionDao = null;
        tripDao = null;
        tripRuntimeExceptionDao = null;
    }
}

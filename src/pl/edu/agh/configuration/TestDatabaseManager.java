package pl.edu.agh.configuration;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Created by Magda on 2014-12-02.
 */
public class TestDatabaseManager {

	private static TestDatabaseHelper databaseHelper;

	public static TestDatabaseHelper getDatabaseHelper(Context context) {
		if ( databaseHelper == null ) {
			databaseHelper = OpenHelperManager.getHelper(context, TestDatabaseHelper.class);
		}
		return databaseHelper;
	}
}

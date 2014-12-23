package pl.edu.agh.services.implementation;

import android.content.Intent;
import android.os.IBinder;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.services.interfaces.IBaseService;
import pl.edu.agh.services.interfaces.ILogService;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class BaseService extends OrmLiteBaseService<TestDatabaseHelper> implements IBaseService {

    private ILogService logService = new AndroidLogService();

    @Override
    public ILogService getLogService() {
        return logService;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

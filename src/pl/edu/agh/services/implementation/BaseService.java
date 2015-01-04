package pl.edu.agh.services.implementation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;
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

    // <editor-fold desc="Validation">

    @Override
    public boolean validateServerResponse(ResponseSerializer response) throws SynchronizationException {
        switch ( response.getStatus() ) {
            case ACCESS_DENIED:
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.ACCESS_DENIED);

            case REQUEST_DENIED:
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.REQUEST_DENIED);

            case VALIDATION_ERROR:
//                throw new SynchronizationException(response.getErrorMessage()); // should never occur
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.UNKNOWN_ERROR);    // TODO: fixme

            case ZERO_RESULTS:
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.ZERO_RESULTS);

            case SERVER_SIDE_ERROR:
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.SERVER_SIDE_ERROR);

            case UNKNOWN_ERROR:
                throw new SynchronizationException(SynchronizationException.PredefinedExceptions.UNKNOWN_ERROR);
        }

        return response.getStatus() == ResponseStatus.OK;
    }

    // </editor-fold>

    @Override
    public boolean hasActiveInternetConnection(Context context) throws SynchronizationException {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

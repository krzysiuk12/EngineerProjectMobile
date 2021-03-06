package pl.edu.agh.services.interfaces;

import android.content.Context;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public interface IBaseService {

    public ILogService getLogService();

    boolean validateServerResponse(ResponseSerializer response) throws SynchronizationException;

    boolean hasActiveInternetConnection(Context context) throws SynchronizationException;
}

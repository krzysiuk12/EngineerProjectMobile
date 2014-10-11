package pl.edu.agh.services.implementation;

import pl.edu.agh.services.interfaces.IBaseService;
import pl.edu.agh.services.interfaces.ILogService;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class BaseService implements IBaseService {

    private ILogService logService = new AndroidLogService();

    @Override
    public ILogService getLogService() {
        return logService;
    }
}

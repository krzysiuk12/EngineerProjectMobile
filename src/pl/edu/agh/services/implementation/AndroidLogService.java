package pl.edu.agh.services.implementation;

import android.util.Log;
import pl.edu.agh.services.interfaces.ILogService;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class AndroidLogService implements ILogService {

    @Override
    public void info(String loggerTag, String message) {
        Log.i(loggerTag, message);
    }

    @Override
    public void debug(String loggerTag, String message) {
        Log.d(loggerTag, message);
    }

    @Override
    public void error(String loggerTag, String message) {
        Log.e(loggerTag, message);
    }

    @Override
    public void error(String loggerTag, String message, Throwable ex) {
        Log.e(loggerTag, message, ex);
    }

    @Override
    public void info(String message) {
        Log.i(DEFAULT_LOG_TAG, message);
    }

    @Override
    public void debug(String message) {
        Log.d(DEFAULT_LOG_TAG, message);
    }

    @Override
    public void error(String message) {
        Log.e(DEFAULT_LOG_TAG, message);
    }

    @Override
    public void error(String message, Throwable ex) {
        Log.e(DEFAULT_LOG_TAG, message, ex);
    }
}

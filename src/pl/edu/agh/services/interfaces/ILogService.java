package pl.edu.agh.services.interfaces;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public interface ILogService {

    public static final String DEFAULT_LOG_TAG = "";

    public void info(String message);

    public void info(String loggerTag, String message);

    public void debug(String message);

    public void debug(String loggerTag, String message);

    public void error(String message);

    public void error(String loggerTag, String message);

    public void error(String message, Throwable ex);

    public void error(String loggerTag, String message, Throwable ex);

}

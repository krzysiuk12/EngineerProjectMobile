package pl.edu.agh.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Magda on 2014-12-28.
 */
public class TimeUtils {

	public static Date formatDateForDatabase(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 19);

		return calendar.getTime();
	}
}

package pl.edu.agh.utils;

import android.content.Context;

import java.util.Collection;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class StringUtils {

    public static String getString(Context context, int stringResourceId) {
        return context.getResources().getString(stringResourceId);
    }

    public String getFormattedString(Context context, int stringResourceId, Collection<? extends Object> parameters) {
        return String.format(getString(context, stringResourceId), parameters);
    }

}

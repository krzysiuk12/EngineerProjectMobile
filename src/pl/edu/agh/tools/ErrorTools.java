package pl.edu.agh.tools;

import android.app.Activity;
import android.content.res.Resources;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.util.List;

/**
 * Created by Magda on 2014-12-02.
 */
public class ErrorTools {

	public static final String NEW_LINE = "\n";

	public static String createFormValidationErrorString(Resources resources, List<FormValidationError> errors) {
		StringBuilder stringBuilder = new StringBuilder();
		for ( FormValidationError error : errors ) {
			stringBuilder.append(resources.getString(error.getStringResourceId())).append(NEW_LINE);
		}
		return stringBuilder.toString();
	}

	public static String createExceptionString(Resources resources, IExceptionDefinition exceptionDefinition) {
		return resources.getString(exceptionDefinition.getStringResourceId());
	}

}

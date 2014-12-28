package pl.edu.agh.tools;

import android.content.res.Resources;
import pl.edu.agh.exceptions.ErrorMessages;
import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;

import java.util.List;

/**
 * Created by Magda on 2014-12-02.
 */
public class ErrorTools {

	public static final String NEW_LINE = "\n";

	public static String createExceptionString(Resources resources, BaseException exception) {
		if ( exception.getExceptionDefinition() != null ) {
			return createExceptionDefinitionString(resources, exception.getExceptionDefinition());
		} else if ( exception.getFormValidationErrors() != null ) {
			return  createFormValidationErrorString(resources, exception.getFormValidationErrors());
		} else {
			return null;
		}
	}

	public static String createFormValidationErrorString(Resources resources, List<FormValidationError> errors) {
		StringBuilder stringBuilder = new StringBuilder();
		for ( FormValidationError error : errors ) {
			stringBuilder.append(resources.getString(error.getStringResourceId())).append(NEW_LINE);
		}
		return stringBuilder.toString();
	}

	public static String createExceptionDefinitionString(Resources resources, IExceptionDefinition exceptionDefinition) {
		return resources.getString(exceptionDefinition.getStringResourceId());
	}

}

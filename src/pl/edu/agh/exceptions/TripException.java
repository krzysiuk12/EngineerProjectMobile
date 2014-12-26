package pl.edu.agh.exceptions;

import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;

import java.util.List;

/**
 * Created by Magda on 2014-12-03.
 */
public class TripException extends BaseException {

	public enum PredefinedExceptions implements IExceptionDefinition {

		// TODO: add missing exceptions
		VALIDATION_NAME_IS_REQUIRED(R.string.TripException_ValidationError_Trip_NameIsRequired, ExceptionType.WARNING),
		VALIDATION_START_DATE_IS_REQUIRED(R.string.TripException_ValidationError_Trip_StartDateIsRequired, ExceptionType.WARNING),
		VALIDATION_END_DATE_IS_REQUIRED(R.string.TripException_ValidationError_Trip_EndDateIsRequired, ExceptionType.WARNING),
		VALIDATION_TRIP_DAYS_IS_REQUIRED(R.string.TripException_ValidationError_Trip_TripDaysIsRequired, ExceptionType.WARNING),
		VALIDATION_START_DATE_BEFORE_END_DATE(R.string.TripException_ValidationError_Trip_StartDateBeforeEndDate, ExceptionType.WARNING),

		VALIDATION_TRIP_DAY_DATE_IS_REQUIRED(R.string.TripException_ValidationError_TripDay_DateIsRequired, ExceptionType.WARNING),
		VALIDATION_TRIP_DAY_INVALID_DATE(R.string.TripException_ValidationError_TripDay_InvalidDate, ExceptionType.WARNING),
		VALIDATION_TRIP_DAY_LOCATIONS_ARE_REQUIRED(R.string.TripException_ValidationError_TripDay_TripDayLocationsAreRequired, ExceptionType.WARNING),

		VALIDATION_TRIP_DAY_LOCATION_LOCATION_IS_REQUIRED(R.string.TripException_ValidationError_TripDayLocation_LocationIsRequired, ExceptionType.WARNING),
		VALIDATION_TRIP_DAY_LOCATION_ORDINAL_IS_REQUIRED(R.string.TripException_ValidationError_TripDayLocation_OrdinalIsRequired, ExceptionType.WARNING);

		private final int stringResourceId;
		private final ExceptionType exceptionType;

		private PredefinedExceptions(int stringResourceId, ExceptionType exceptionType) {
			this.stringResourceId = stringResourceId;
			this.exceptionType = exceptionType;
		}

		@Override
		public int getStringResourceId() {
			return stringResourceId;
		}

		@Override
		public ExceptionType getExceptionType() {
			return exceptionType;
		}
	}


	public TripException(IExceptionDefinition exceptionDefinition) {
		super(exceptionDefinition);
	}

	public TripException(List<FormValidationError> formValidationErrors) {
		super(formValidationErrors);
	}

	public TripException(String detailMessage, IExceptionDefinition exceptionDefinition) {
		super(detailMessage, exceptionDefinition);
	}

	public TripException(String detailMessage, List<FormValidationError> formValidationErrors) {
		super(detailMessage, formValidationErrors);
	}

	public TripException(BaseException ex) {
		super(ex);
	}

}


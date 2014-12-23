package pl.edu.agh.exceptions;

import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;

import java.util.List;

/**
 * Created by Magda on 2014-12-23.
 */
public class SynchronizationException extends BaseException {

	public enum PredefinedExceptions implements IExceptionDefinition {
		SERVER_ERROR(R.string.SynchronizationException_ServerError, ExceptionType.FATAL),
		SERVER_UNREACHABLE(R.string.SynchronizationException_ServerUnreachable, ExceptionType.ERROR),
		DATABASE_ERROR(R.string.SynchronizationException_DatabaseSaveError, ExceptionType.ERROR);

		private int stringResourceId;
		private ExceptionType exceptionType;

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

	public SynchronizationException(IExceptionDefinition exceptionDefinition) {
		super(exceptionDefinition);
	}

	public SynchronizationException(List<FormValidationError> formValidationErrors) {
		super(formValidationErrors);
	}

}

package pl.edu.agh.exceptions;

import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class GoogleGeocodingException extends BaseException {

    public enum PredefinedExceptions implements IExceptionDefinition {

        FAILED_TO_GET_LOCATION_DESCRIPTION(R.string.GoogleGeocodingException_FAILED_TO_GET_LOCATION_DESCRIPTION, ExceptionType.ERROR),
        NOT_DEFINED(R.string.GoogleDirectionsException_NOT_DEFINED, ExceptionType.ERROR);

        private final int stringResourceId;
        private final ExceptionType exceptionType;

        private PredefinedExceptions(int stringResourceId, ExceptionType exceptionType) {
            this.stringResourceId = stringResourceId;
            this.exceptionType = exceptionType;
        }

        @Override
        public int getStringResourceId() {
            return 0;
        }

        @Override
        public ExceptionType getExceptionType() {
            return null;
        }
    }

    public GoogleGeocodingException(IExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition);
    }

    public GoogleGeocodingException(List<FormValidationError> formValidationErrors) {
        super(formValidationErrors);
    }

    public GoogleGeocodingException(String detailMessage, IExceptionDefinition exceptionDefinition) {
        super(detailMessage, exceptionDefinition);
    }

    public GoogleGeocodingException(String detailMessage, List<FormValidationError> formValidationErrors) {
        super(detailMessage, formValidationErrors);
    }

    public GoogleGeocodingException(BaseException ex) {
        super(ex);
    }
}

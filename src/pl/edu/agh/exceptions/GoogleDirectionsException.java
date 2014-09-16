package pl.edu.agh.exceptions;

import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class GoogleDirectionsException extends BaseException {

    public enum PredefinedExceptions implements IExceptionDefinition {
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

    public GoogleDirectionsException(IExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition);
    }

    public GoogleDirectionsException(List<FormValidationError> formValidationErrors) {
        super(formValidationErrors);
    }

    public GoogleDirectionsException(String detailMessage, IExceptionDefinition exceptionDefinition) {
        super(detailMessage, exceptionDefinition);
    }

    public GoogleDirectionsException(String detailMessage, List<FormValidationError> formValidationErrors) {
        super(detailMessage, formValidationErrors);
    }

    public GoogleDirectionsException(BaseException ex) {
        super(ex);
    }
}

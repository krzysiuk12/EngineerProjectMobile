package pl.edu.agh.exceptions.common;

import pl.edu.agh.exceptions.ErrorMessages;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-18.
 */
public abstract class BaseException extends Exception {

    private IExceptionDefinition exceptionDefinition;
    private List<FormValidationError> formValidationErrors;
    private ErrorMessages errorMessages;

    protected BaseException(IExceptionDefinition exceptionDefinition) {
        this.exceptionDefinition = exceptionDefinition;
    }

    protected BaseException(List<FormValidationError> formValidationErrors) {
        this.formValidationErrors = formValidationErrors;
    }

    protected BaseException(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }

    protected BaseException(String detailMessage, IExceptionDefinition exceptionDefinition) {
        super(detailMessage);
        this.exceptionDefinition = exceptionDefinition;
    }

    protected BaseException(String detailMessage, List<FormValidationError> formValidationErrors) {
        super(detailMessage);
        this.formValidationErrors = formValidationErrors;
    }

    protected BaseException(BaseException ex) {
        if(ex != null) {
            this.exceptionDefinition = ex.getExceptionDefinition();
            this.formValidationErrors = ex.getFormValidationErrors();
        }
    }

    public IExceptionDefinition getExceptionDefinition() {
        return exceptionDefinition;
    }

    public void setExceptionDefinition(IExceptionDefinition exceptionDefinition) {
        this.exceptionDefinition = exceptionDefinition;
    }

    public List<FormValidationError> getFormValidationErrors() {
        return formValidationErrors;
    }

    public void setFormValidationErrors(List<FormValidationError> formValidationErrors) {
        this.formValidationErrors = formValidationErrors;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(ErrorMessages errorMessages) {
        this.errorMessages = errorMessages;
    }
}

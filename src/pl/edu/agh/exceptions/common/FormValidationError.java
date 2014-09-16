package pl.edu.agh.exceptions.common;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class FormValidationError {

    private int stringResourceId;
    private List<Object> parameters;

    public FormValidationError(int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    public FormValidationError(int stringResourceId, List<Object> parameters) {
        this.stringResourceId = stringResourceId;
        this.parameters = parameters;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

    public void setStringResourceId(int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}

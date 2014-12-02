package pl.edu.agh.exceptions;

import pl.edu.agh.exceptions.common.BaseException;
import pl.edu.agh.exceptions.common.ExceptionType;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.exceptions.common.IExceptionDefinition;
import pl.edu.agh.main.R;

import java.util.List;

/**    <string name="LocationException.ValidationError.NameIsRequired">Name field is required</string>
 <string name="LocationException.ValidationError.LongitudeIsRequired">Longitude field is required</string>
 <string name="LocationException.ValidationError.LatitudeIsRequired">Latitude field is required</string>
 <string name="LocationException.ValidationError.StatusIsRequired">Status field is required</string>
 <string name="LocationException.ValidationError.CreatedByAccountIsRequired">CreatedByAccount field is required</string>
 <string name="LocationException.ValidationError.CreationDateIsRequired">CreationDate field is required</string>
 <string name="LocationException.ValidationError.AddressIsRequired">Address field is required</string>
 * Created by Krzysiu on 2014-09-15.
 */
public class LocationException extends BaseException {

    public enum PredefinedExceptions implements IExceptionDefinition {

        VALIDATION_NAME_IS_REQUIRED(R.string.LocationException_ValidationError_NameIsRequired, ExceptionType.WARNING),
        VALIDATION_LONGITUDE_IS_REQUIRED(R.string.LocationException_ValidationError_LongitudeIsRequired, ExceptionType.WARNING),
        VALIDATION_LATITUDE_IS_REQUIRED(R.string.LocationException_ValidationError_LatitudeIsRequired, ExceptionType.WARNING),
        VALIDATION_STATUS_IS_REQUIRED(R.string.LocationException_ValidationError_StatusIsRequired, ExceptionType.WARNING),
        VALIDATION_CREATED_BY_ACCOUNT_IS_REQUIRED(R.string.LocationException_ValidationError_CreatedByAccountIsRequired, ExceptionType.WARNING),
        VALIDATION_CREATION_DATE_IS_REQUIRED(R.string.LocationException_ValidationError_CreationDateIsRequired, ExceptionType.WARNING),
        VALIDATION_ADDRESS_IS_REQUIRED(R.string.LocationException_ValidationError_AddressIsRequired, ExceptionType.WARNING),
        VALIDATION_CITY_IS_REQUIRED(R.string.LocationException_ValidationError_CityIsRequired, ExceptionType.WARNING),
        VALIDATION_COUNTRY_IS_REQUIRED(R.string.LocationException_ValidationError_CountryIsRequired, ExceptionType.WARNING);

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

    public LocationException(IExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition);
    }

    public LocationException(List<FormValidationError> formValidationErrors) {
        super(formValidationErrors);
    }

    public LocationException(String detailMessage, IExceptionDefinition exceptionDefinition) {
        super(detailMessage, exceptionDefinition);
    }

    public LocationException(String detailMessage, List<FormValidationError> formValidationErrors) {
        super(detailMessage, formValidationErrors);
    }

    public LocationException(BaseException ex) {
        super(ex);
    }
}

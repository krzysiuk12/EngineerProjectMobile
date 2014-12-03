package pl.edu.agh.services.implementation;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.DatabaseHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.configuration.TestDatabaseManager;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.main.R;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.repositories.interfaces.ILocationRepository;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.tools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class LocationManagementService implements ILocationManagementService {

	private static LocationManagementService instance;
	private DatabaseHelper databaseHelper;
	private ILocationRepository locationRepository;

	public LocationManagementService(Context context) {
		locationRepository = new OrmLiteLocationRepository(TestDatabaseManager.getDatabaseHelper(context));
	}

	@Override
	public List<FormValidationError> validateLocation(Location location) throws LocationException {
		List<FormValidationError> errors = new ArrayList<>();
		if ( StringTools.isNullOrEmpty(location.getName()) ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_NAME_IS_REQUIRED.getStringResourceId()));
		}
		if ( location.getLatitude() == 0.0 ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_LATITUDE_IS_REQUIRED.getStringResourceId()));
		}
		if ( location.getLongitude() == 0.0 ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_LONGITUDE_IS_REQUIRED.getStringResourceId()));
		}
		if ( location.getStatus() == null ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_STATUS_IS_REQUIRED.getStringResourceId()));
		}
		if ( location.getAddress() == null ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_ADDRESS_IS_REQUIRED.getStringResourceId()));
		}
		if ( location.getCreationDate() == null ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_CREATION_DATE_IS_REQUIRED.getStringResourceId()));
		}

		errors.addAll(validateAddress(location.getAddress()));

		if ( !errors.isEmpty() ) {
			throw new LocationException(errors);    // todo: move to saveLocation?
		}
		return errors;
	}

	@Override
	public void saveLocation(Location location) throws LocationException {
		List<FormValidationError> errors = validateLocation(location);
		if ( !errors.isEmpty() ) {
			throw new LocationException(errors);
		}
		locationRepository.saveLocation(location);
	}

	@Override
	public Location getLocationById(Long id) throws LocationException {
		return locationRepository.getLocationById(id);
	}

	@Override
	public Location getLocationByName(String name) throws LocationException {
//		if ( StringTools.isNullOrEmpty(name) )
//			throw new LocationException(0);
		return locationRepository.getLocationByName(name);
	}

	@Override
	public Location getLocationByCoordinates(double longitude, double latitude) throws LocationException {
		return locationRepository.getLocationByCoordinates(longitude, latitude);
	}

	@Override
	public List<Location> getAllUserLocations(UserAccount account) throws LocationException {
		return null;
	}

	@Override
	public List<Location> getAllUserPrivateLocations(UserAccount account) throws LocationException {
		return null;
	}

	private List<FormValidationError> validateAddress(Address address) {
		List<FormValidationError> errors = new ArrayList<>();
		if ( StringTools.isNullOrEmpty(address.getCountry()) ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_COUNTRY_IS_REQUIRED.getStringResourceId()));
		}
		if ( StringTools.isNullOrEmpty(address.getCity()) ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_CITY_IS_REQUIRED.getStringResourceId()));
		}

		return errors;
	}

}

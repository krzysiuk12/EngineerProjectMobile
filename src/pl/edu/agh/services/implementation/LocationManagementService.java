package pl.edu.agh.services.implementation;

import android.content.Context;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.common.FormValidationError;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.repositories.interfaces.ILocationRepository;
import pl.edu.agh.services.interfaces.ILocationManagementService;
import pl.edu.agh.tools.StringTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class LocationManagementService extends BaseService implements ILocationManagementService {

	private ILocationRepository locationRepository;

	public LocationManagementService(Context context) {
		locationRepository = new OrmLiteLocationRepository(getHelperInternal(context));
	}

	public LocationManagementService(OrmLiteSqliteOpenHelper helper) {
		locationRepository = new OrmLiteLocationRepository(helper);
	}

	// <editor-fold desc="Validation">

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
//		if ( location.getCreationDate() == null ) {
//			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_CREATION_DATE_IS_REQUIRED.getStringResourceId()));
//		}

		errors.addAll(validateAddress(location.getAddress()));
		return errors;
	}

	private List<FormValidationError> validateAddress(Address address) {
		List<FormValidationError> errors = new ArrayList<>();
		if ( address == null ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_ADDRESS_IS_REQUIRED.getStringResourceId()));
			return errors;
		}
		if ( StringTools.isNullOrEmpty(address.getCountry()) ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_COUNTRY_IS_REQUIRED.getStringResourceId()));
		}
		if ( StringTools.isNullOrEmpty(address.getCity()) ) {
			errors.add(new FormValidationError(LocationException.PredefinedExceptions.VALIDATION_CITY_IS_REQUIRED.getStringResourceId()));
		}

		return errors;
	}

	// </editor-fold>

	@Override
	public int saveLocation(Location location) throws LocationException {
		location.setCreationDate(new Date());
		List<FormValidationError> errors = validateLocation(location);
		if ( !errors.isEmpty() ) {
			throw new LocationException(errors);
		}
		return locationRepository.saveLocation(location);
	}

	@Override
	public int saveNewLocation(Location location, UserAccount userAccount) throws LocationException {
		location.setSynced(false);
		location.setCreationDate(new Date());
		location.setCreatedByAccount(userAccount);
		return saveLocation(location);
	}

	@Override
	public void updateLocation(Location location) throws LocationException {
		List<FormValidationError> errors = validateLocation(location);
		if ( !errors.isEmpty() ) {
			throw new LocationException(errors);
		}
		locationRepository.updateLocation(location);
	}

	@Override
	public void saveOrUpdateLocation(Location location) throws LocationException {
		Location locationInDatabase = locationRepository.getLocationByGlobalId(location.getGlobalId());
		if ( locationInDatabase == null ) {
			saveLocation(location);
		} else {
			location.setId(locationInDatabase.getId());
			updateLocation(location);
		}
	}

	// <editor-fold desc="Get Location By">

	@Override
	public Location getLocationById(Long id) throws LocationException {
		return locationRepository.getLocationById(id);
	}

	@Override
	public Location getLocationByGlobalId(Long id) throws LocationException {
		return locationRepository.getLocationByGlobalId(id);
	}

	@Override
	public Location getLocationByName(String name) throws LocationException {
		return locationRepository.getLocationByName(name);
	}

	@Override
	public Location getLocationByCoordinates(double latitude, double longitude) throws LocationException {
		return locationRepository.getLocationByCoordinates(longitude, latitude);
	}

	// </editor-fold>

	// <editor-fold desc="Get Locations">

	@Override
	public List<Location> getAllLocations() throws LocationException {
		return locationRepository.getAllLocations();
	}

	@Override
	public List<Location> getAllLocations(UserAccount userAccount) throws LocationException {
		List<Location> publicLocations = locationRepository.getAllPublicLocations();
		List<Location> privateLocations = getAllUserPrivateLocations(userAccount);
		List<Location> locations = new ArrayList<>();
		locations.addAll(publicLocations);
		locations.addAll(privateLocations);
		return locations;
	}

	@Override
	public List<Location> getAllUserLocations(UserAccount account) throws LocationException {
		return locationRepository.getAllUserLocations(account);
	}

	@Override
	public List<Location> getAllUserPrivateLocations(UserAccount userAccount) throws LocationException {
		return locationRepository.getAllUserPrivateLocations(userAccount);
	}

	@Override
	public List<Location> getAllNewLocations() throws LocationException {
		List<Location> privateLocations = getAllNewPrivateLocations();
		List<Location> publicLocations = getAllNewPublicLocations();
		List<Location> allLocations = new ArrayList<>();
		allLocations.addAll(privateLocations);
		allLocations.addAll(publicLocations);
		return allLocations;
	}

	@Override
	public List<Location> getAllNewPrivateLocations() throws LocationException {
		return locationRepository.getAllNewPrivateLocations();
	}

	@Override
	public List<Location> getAllNewPublicLocations() throws LocationException {
		return locationRepository.getAllNewPublicLocations();
	}

	// </editor-fold>

	// <editor-fold desc="Delete Locations">

	@Override
	public void deletePublicLocations() throws LocationException {
		locationRepository.deletePublicLocations();
	}

	@Override
	public void deletePrivateLocations(UserAccount userAccount) throws LocationException{
		locationRepository.deletePrivateLocations(userAccount);
	}

	// </editor-fold>

}

package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.exceptions.common.FormValidationError;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public interface ILocationManagementService {

    public List<FormValidationError> validateLocation(Location location) throws LocationException;

    public int saveLocation(Location location) throws LocationException;

    public int saveNewLocation(Location location, UserAccount userAccount) throws LocationException;

    public void updateLocation(Location location) throws LocationException;

    public void saveOrUpdateLocation(Location location) throws LocationException;

    public Location getLocationById(Long id) throws LocationException;

    public Location getLocationByGlobalId(Long id) throws LocationException;

    public Location getLocationByName(String name) throws LocationException;

    public Location getLocationByCoordinates(double latitude, double longitude) throws LocationException;

    List<Location> getAllLocations() throws LocationException;

    public List<Location> getAllLocations(UserAccount userAccount) throws LocationException;

    public List<Location> getAllUserLocations(UserAccount account) throws LocationException;

    public List<Location> getAllUserPrivateLocations(UserAccount userAccount) throws LocationException;

    public List<Location> getAllNewLocations() throws LocationException;

    public List<Location> getAllNewPrivateLocations() throws LocationException;

    public List<Location> getAllNewPublicLocations() throws LocationException;

    void deletePublicLocations() throws LocationException;

    void deletePrivateLocations(UserAccount userAccount) throws LocationException;
}

package pl.edu.agh.repositories.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public interface ILocationRepository {

    public int saveLocation(Location location) throws LocationException;

    public void updateLocation(Location loation) throws LocationException;

    public Location getLocationById(Long id) throws LocationException;

    public Location getLocationByGlobalId(Long id) throws LocationException;

    public Location getLocationByName(String name) throws LocationException;

    public Location getLocationByCoordinates(double longitude, double latitude) throws LocationException;

	public List<Location> getAllLocations() throws LocationException;

    public List<Location> getAllPublicLocations() throws LocationException;

    public List<Location> getAllUserLocations(UserAccount account) throws LocationException;

    public List<Location> getAllUserPrivateLocations(UserAccount account) throws LocationException;

    public List<Location> getAllNewPublicLocations() throws LocationException;

    public List<Location> getAllNewPrivateLocations() throws LocationException;
}

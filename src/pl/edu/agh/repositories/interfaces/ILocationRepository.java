package pl.edu.agh.repositories.interfaces;

import pl.edu.agh.domain.Location;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public interface ILocationRepository {

    public void saveLocation(Location location);
    public List<Location> getAllLocations();

}

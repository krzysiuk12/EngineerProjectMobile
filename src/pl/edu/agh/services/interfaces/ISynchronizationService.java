package pl.edu.agh.services.interfaces;

/**
 * Created by Magda on 2014-11-21.
 */
public interface ISynchronizationService {

	public void downloadAllLocations();

	public void downloadTrips();

	public void sendNewPublicLocations();

	public void sendNewPrivateLocations();

}

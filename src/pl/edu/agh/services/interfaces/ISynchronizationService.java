package pl.edu.agh.services.interfaces;

import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Magda on 2014-11-21.
 */
public interface ISynchronizationService {

	public void downloadAllLocations();

	public void downloadAllPrivateLocations(String token);

	public void downloadLocationsInScope(double latitude, double longitude, double scope);

	public void downloadTrips(String token);

	public void sendNewPublicLocations() throws SynchronizationException;

	public void sendNewPrivateLocations() throws SynchronizationException;

	public void sendAllNewLocations() throws SynchronizationException;

	public void sendTrips(String token) throws SynchronizationException;
}

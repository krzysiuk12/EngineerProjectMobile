package pl.edu.agh.services.interfaces;

import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Magda on 2014-11-21.
 */
public interface ISynchronizationService {

	public void downloadAllLocations() throws SynchronizationException;

	public void downloadAllPrivateLocations(String token) throws SynchronizationException;

	public void downloadLocationsInScope(double latitude, double longitude, double scope) throws SynchronizationException;

	public void downloadTrips(UserAccount userAccount) throws SynchronizationException;

	public void sendNewPublicLocations() throws SynchronizationException;

	public void sendNewPrivateLocations() throws SynchronizationException;

	public void sendAllNewLocations() throws SynchronizationException;

	public void sendTrips(String token) throws SynchronizationException;
}

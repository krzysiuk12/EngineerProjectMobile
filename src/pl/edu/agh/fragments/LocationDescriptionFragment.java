package pl.edu.agh.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import pl.edu.agh.asynctasks.locations.GetLocationByIdAsyncTask;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-14.
 */
public class LocationDescriptionFragment extends AbstractDescriptionFragment {

//	public static LocationDescriptionFragment newInstance(Location location) {
//		LocationDescriptionFragment fragment = new LocationDescriptionFragment();
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("location", location);
//		fragment.setArguments(bundle);
//		return fragment;
//	}

	public static LocationDescriptionFragment newInstance(long index) {
		LocationDescriptionFragment fragment = new LocationDescriptionFragment();
		fragment.setInitialArguments(index);
		return fragment;
	}

	@Override
	protected void setLayout(LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.location_description_fragment, container, false);
	}

	@Override
	protected void showDetails() {
		Location location = null;
		//TODO: replace with local db
		try {
			location = new GetLocationByIdAsyncTask(UserAccountManagementService.getToken(), getDisplayedItemIndex()).execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if ( location != null ) {
			setTextViewText(R.id.LocationDescription_Name, location.getName());
			setTextViewText(R.id.LocationDescription_Status, location.getStatus().toString());
			setTextViewText(R.id.LocationDescription_Description, location.getDescription());
			setTextViewText(R.id.LocationDescription_Address, getFormattedAddress(location.getAddress()));
			setTextViewText(R.id.LocationDescription_Rating, location.getRating() + "");
			setTextViewText(R.id.LocationDescription_CreatedBy, location.getCreatedByAccount() + "");
		}
	}



	public String getFormattedAddress(Address address) {
		StringBuilder builder = new StringBuilder();
		if ( address.getStreet() != null )
			builder.append(address.getStreet()).append(", ");
		if ( address.getPostalCode() != null )
			builder.append(address.getPostalCode()).append(" ");
		if ( address.getCity() != null )
			builder.append(address.getCity()).append(", ");
		if ( address.getCountry() != null)
			builder.append(address.getCountry());
		return builder.toString();
	}
}

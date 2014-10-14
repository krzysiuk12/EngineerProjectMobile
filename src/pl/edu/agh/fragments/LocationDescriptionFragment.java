package pl.edu.agh.fragments;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-14.
 */
public class LocationDescriptionFragment extends AbstractDescriptionFragment<Location> {

	public static LocationDescriptionFragment newInstance(Location location, long index) {
		LocationDescriptionFragment fragment = new LocationDescriptionFragment();
		fragment.setInitialArguments(index, location);
		return fragment;
	}

	@Override
	protected void setLayout(LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(R.layout.location_description_fragment, container, false);
	}

	@Override
	protected void showDetails() {
		Location location = (Location) getArguments().getSerializable("listItem");

		if ( location != null ) {
			setTextViewText(R.id.LocationDescription_Name, location.getName());
			setTextViewText(R.id.LocationDescription_Status, location.getStatus().toString());
			setTextViewText(R.id.LocationDescription_Description, location.getDescription());
			setTextViewText(R.id.LocationDescription_Address, getFormattedAddress(location.getAddress()));
			setTextViewText(R.id.LocationDescription_Rating, location.getRating() + "");
			setTextViewText(R.id.LocationDescription_CreatedBy, location.getCreatedByAccount() + "");
		}
	}

	private String getFormattedAddress(Address address) {
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

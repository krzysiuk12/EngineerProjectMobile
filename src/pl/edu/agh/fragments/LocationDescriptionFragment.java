package pl.edu.agh.fragments;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-14.
 */
public class LocationDescriptionFragment extends AbstractDescriptionFragment<Location> {

	private static final int layoutId = R.layout.location_description_fragment;

	public static LocationDescriptionFragment newInstance(Location location, long index) {
		LocationDescriptionFragment fragment = new LocationDescriptionFragment();
		fragment.setInitialArguments(index, location);
		return fragment;
	}

	@Override
	protected void showDetails() {
		Location location = (Location) getArguments().getSerializable(KEY_ITEM);

		if ( location != null ) {
			RenderingTools.setTextViewText(view, R.id.LocationDescription_Name, location.getName());
			RenderingTools.setTextViewText(view, R.id.LocationDescription_Status, location.getStatus().toString());
			RenderingTools.setTextViewText(view, R.id.LocationDescription_Description, location.getDescription());
			RenderingTools.setTextViewText(view, R.id.LocationDescription_Address, location.getAddress().toString());
			RenderingTools.setTextViewText(view, R.id.LocationDescription_Rating, location.getRating() + "");
			RenderingTools.setTextViewText(view, R.id.LocationDescription_CreatedBy,
					(location.getCreatedByAccount() != null ? location.getCreatedByAccount().getLogin() : "unknown"));
		}
	}

	@Override
	protected int getLayoutId() {
		return layoutId;
	}
}

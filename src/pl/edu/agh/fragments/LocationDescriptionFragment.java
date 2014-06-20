package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pl.edu.agh.asynctasks.GetLocationByIdAsyncTask;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-14.
 */
public class LocationDescriptionFragment extends Fragment {
	private View view;

	public static LocationDescriptionFragment newInstance(long index) {
		LocationDescriptionFragment f = new LocationDescriptionFragment();

		Bundle args = new Bundle();
		args.putLong("index", index);
		f.setArguments(args);

		return f;
	}

	public long getDisplayedItemIndex() {
		return getArguments() != null ? getArguments().getLong("index", 0) : 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.location_description_fragment, container, false);

		Location location = null;
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
			setTextViewText(R.id.LocationDescription_Address, location.getAddress().toString());
			setTextViewText(R.id.LocationDescription_Rating, location.getRating() + "");
			//setTextViewText(R.id.LocationDescription_CreatedBy, location.getCreatedByAccount() + "");
		}

		return view;
	}

	private void setTextViewText(int textViewId, String value) {
		TextView textView = (TextView) view.findViewById(textViewId);
		textView.setText(value);
	}
}

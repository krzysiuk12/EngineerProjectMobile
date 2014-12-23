package pl.edu.agh.fragments.locations;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Magdalena Strzoda on 2014-06-16.
 */
public class LocationAdapter extends AbstractAdapter<Location> {

	public LocationAdapter(Context context, ArrayList<Location> locations) {
		super(context, R.layout.list_item, locations);
	}

	@Override
	protected void setLabels(View view, Location location) {
		RenderingTools.setTextViewText(view, R.id.ListItem_Name, location.getName());
	}
}

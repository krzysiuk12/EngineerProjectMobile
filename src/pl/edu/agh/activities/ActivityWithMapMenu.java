package pl.edu.agh.activities;

import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.main.R;
import pl.edu.agh.services.interfaces.IGoogleMapsManagementService;

/**
 * Created by Magda on 2014-12-26.
 */
public abstract class ActivityWithMapMenu extends OrmLiteBaseActivity<TestDatabaseHelper> {

	//<editor-fold desc="ActionBar Menu - Methods">

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(getMenuLayoutId(), menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.MapMenu_Help:
				helpMenuAction();
				return true;
			case R.id.MapMenu_Settings_GoogleMapsType_Hybrid:
				getGoogleMapsManagementService().setHybridMapType(getGoogleMap());
				return true;
			case R.id.MapMenu_Settings_GoogleMapsType_Normal:
				getGoogleMapsManagementService().setNormalMapType(getGoogleMap());
				return true;
			case R.id.MapMenu_Settings_GoogleMapsType_Satellite:
				getGoogleMapsManagementService().setSatelliteMapType(getGoogleMap());
				return true;
			case R.id.MapMenu_Settings_GoogleMapsType_Terrain:
				getGoogleMapsManagementService().setTerrainMapType(getGoogleMap());
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// </editor-fold>

	protected int getMenuLayoutId() {
		return R.menu.simple_map_menu;  // default map menu layout
	}

	protected abstract void helpMenuAction();

	protected abstract GoogleMap getGoogleMap();

	protected abstract IGoogleMapsManagementService getGoogleMapsManagementService();

}

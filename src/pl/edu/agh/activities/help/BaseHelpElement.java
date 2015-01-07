package pl.edu.agh.activities.help;

import android.content.res.Resources;
import pl.edu.agh.main.R;

/**
 * Created by Magda on 2015-01-07.
 */
public enum BaseHelpElement {

	MAIN_MENU(R.string.HelpElementDescription_MainMenu_Title, R.layout.help_element_main_menu_fragment),
	ADD_LOCATION(R.string.Help_AddLocation_Title, R.layout.help_add_location_fragment);

	private int stringResourceId;

	private int layoutId;

	BaseHelpElement(int stringResourceId, int layoutId) {
		this.stringResourceId = stringResourceId;
		this.layoutId = layoutId;
	}

	public HelpElement getHelpElement(Resources resources) {
		return new HelpElement(resources.getString(stringResourceId), layoutId);
	}

}

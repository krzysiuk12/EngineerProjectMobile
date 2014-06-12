package pl.edu.agh.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import pl.edu.agh.main.R;

/**
 * Created by Krzysiu on 2014-06-10.
 */
public class MainMenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

	    ((Button)findViewById(R.id.MainMenu_ShowLocationsButton)).setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    showLocations(view);
		    }
	    });
    }

	private void showLocations(View view) {
		startActivity(new Intent(this, ShowLocationsOnMapActivity.class));
	}
}
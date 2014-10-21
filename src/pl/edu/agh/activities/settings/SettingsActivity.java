package pl.edu.agh.activities.settings;

import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
}
package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.fragments.SettingsIssuePanelFragment;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssuePanelActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(findViewById(R.id.SettingsActivity_SettingsIssuePanel) != null) {
            finish();
            return;
        }

        if(savedInstanceState == null) {
            SettingsIssuePanelFragment fragment = new SettingsIssuePanelFragment();
            fragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
    }
}
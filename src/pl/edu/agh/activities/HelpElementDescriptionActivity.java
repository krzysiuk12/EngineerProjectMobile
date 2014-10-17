package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.fragments.HelpElementDescriptionFragment;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-10-13.
 */
public class HelpElementDescriptionActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(findViewById(R.id.HelpActivity_HelpElementDescription) != null) {
            finish();
            return;
        }

        if(savedInstanceState == null) {
            HelpElementDescriptionFragment descriptionFragment = new HelpElementDescriptionFragment();
            descriptionFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, descriptionFragment).commit();
        }
    }
}
package pl.edu.agh.activities.help;


import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.activities.help.HelpElement;
import pl.edu.agh.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sławek on 2014-10-12.
 */
public class HelpActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
    }
}
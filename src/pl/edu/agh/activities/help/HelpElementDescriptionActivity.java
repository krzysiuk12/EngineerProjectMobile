package pl.edu.agh.activities.help;

import pl.edu.agh.activities.AbstractDetailsActivity;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.help.HelpElementDescriptionFragment;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-10-13.
 */
public class HelpElementDescriptionActivity extends AbstractDetailsActivity {

    @Override
    protected int getDetailsPaneId() {
        return R.id.HelpActivity_HelpElementDescription;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragment() {
        return new HelpElementDescriptionFragment();
    }
}
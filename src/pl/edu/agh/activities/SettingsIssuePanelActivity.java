package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.SettingsIssuePanelFragment;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssuePanelActivity extends AbstractDetailsActivity {

    @Override
    protected int getDetailsPaneId() {
        return R.id.SettingsActivity_SettingsIssuePanel;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragment() {
        return new SettingsIssuePanelFragment();
    }
}
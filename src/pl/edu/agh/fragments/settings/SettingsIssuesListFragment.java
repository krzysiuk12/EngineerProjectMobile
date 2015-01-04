package pl.edu.agh.fragments.settings;

import pl.edu.agh.activities.settings.ClearDatabaseSettingsIssue;
import pl.edu.agh.activities.settings.SettingsIssuePanelActivity;
import pl.edu.agh.activities.settings.DefaultUserSettingsIssue;
import pl.edu.agh.activities.settings.LanguageSettingsIssue;
import pl.edu.agh.activities.settings.SettingsIssue;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.AbstractListFragment;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssuesListFragment extends AbstractListFragment<SettingsIssue> {

    private int detailsPaneId;

    public SettingsIssuesListFragment() {
        super();
        detailsPaneId = R.id.SettingsActivity_SettingsIssuePanel;
    }

    @Override
    protected AbstractAdapter getAdapterInstance() {
        ArrayList<SettingsIssue> settingsIssuesList = new ArrayList<SettingsIssue>();
        settingsIssuesList.add(new LanguageSettingsIssue(getString(R.string.Settings_LanguageSettings)));
        settingsIssuesList.add(new DefaultUserSettingsIssue(getString(R.string.Settings_DefaultUser)));
        settingsIssuesList.add(new ClearDatabaseSettingsIssue(getString(R.string.Settings_Database_Title)));

        return new SettingsIssueAdapter(getActivity(), settingsIssuesList);
    }

    @Override
    protected Class getClassForDetailsIntent() {
        return SettingsIssuePanelActivity.class;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragmentInstance(SettingsIssue settingsIssue, int index) {
        return SettingsIssuePanelFragment.newInstance(settingsIssue, index);
    }

    @Override
    protected int getDetailsPaneId() {
        return detailsPaneId;
    }
}

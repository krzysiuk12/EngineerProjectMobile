package pl.edu.agh.fragments;

import pl.edu.agh.activities.SettingsIssuePanelActivity;
import pl.edu.agh.activities.settings.SettingsIssue;
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
        settingsIssuesList.add(new SettingsIssue("Ustawienia testowe", R.layout.settings_issue_test_fragment));

        return new SettingsIssueAdapter(getActivity(), settingsIssuesList);
    }

    @Override
    protected Class getClassForDetailsIntent() {
        return SettingsIssuePanelActivity.class;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragmentInstance(SettingsIssue settingsIssue, int index) {
        return new SettingsIssuePanelFragment().newInstance(settingsIssue, index);
    }

    @Override
    protected int getDetailsPaneId() {
        return detailsPaneId;
    }
}

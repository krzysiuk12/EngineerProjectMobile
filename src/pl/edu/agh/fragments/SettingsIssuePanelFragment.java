package pl.edu.agh.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.activities.settings.SettingsIssue;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssuePanelFragment extends AbstractDescriptionFragment<SettingsIssue> {

    private SettingsIssue displayedSettingsIssue;

    public static SettingsIssuePanelFragment newInstance(SettingsIssue settingsIssue, int index) {
        SettingsIssuePanelFragment fragment = new SettingsIssuePanelFragment();
        fragment.setInitialArguments(index, settingsIssue);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        displayedSettingsIssue = (SettingsIssue) getArguments().getSerializable(KEY_ITEM);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        displayedSettingsIssue.initializeView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return displayedSettingsIssue.getLayoutId();
    }

    @Override
    protected void showDetails() {
    }
}
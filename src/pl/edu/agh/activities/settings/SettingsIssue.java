package pl.edu.agh.activities.settings;

import android.view.View;
import pl.edu.agh.domain.common.BaseObject;
import pl.edu.agh.fragments.SettingsIssuePanelFragment;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-10-21.
 */
public abstract class SettingsIssue extends BaseObject implements Serializable {

    private int layoutId;
    private String label;

    private SettingsIssuePanelFragment fragment;

    public SettingsIssue(String label, int layoutId) {
        this.label = label;
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
    public String getLabel() {
        return label;
    }

    public SettingsIssuePanelFragment getFragment() {
        return fragment;
    }

    public void setFragment(SettingsIssuePanelFragment fragment) {
        this.fragment = fragment;
    }

    public abstract void initializeView(View view);
}

package pl.edu.agh.activities.settings;

import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssue extends BaseObject implements Serializable {

    private int layoutId;
    private String label;

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
}

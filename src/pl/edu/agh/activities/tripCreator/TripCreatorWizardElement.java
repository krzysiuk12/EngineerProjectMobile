package pl.edu.agh.activities.tripCreator;

import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardElement extends BaseObject implements Serializable {

    private int layoutId;
    private String label;
    private boolean isEnabled;

    public TripCreatorWizardElement(int layoutId, String label, boolean isEnabled) {

        this.layoutId = layoutId;
        this.label = label;
        this.isEnabled = isEnabled;
    }

    public int getLayoutId() {
        return layoutId;
    }
    public String getLabel() {
        return label;
    }

    public void setIsEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
    public boolean getIsEnabled() { return isEnabled; }
}

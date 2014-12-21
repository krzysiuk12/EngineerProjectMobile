package pl.edu.agh.activities.tripcreator;

import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardElement extends BaseObject implements Serializable {

    private int layoutId;
    private String label;

    public TripCreatorWizardElement(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
    public String getLabel() {
        return label;
    }
}

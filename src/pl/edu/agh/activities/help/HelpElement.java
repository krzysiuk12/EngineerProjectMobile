package pl.edu.agh.activities.help;

import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-10-12.
 */
public class HelpElement extends BaseObject implements Serializable {

    private String titleId;
    private int layoutId;
    private String label;

    public HelpElement(String label, int layoutId) {
        this.label = label;
        this.layoutId = layoutId;
    }

    public int getLayoutId() {
        return layoutId;
    }
    public String getTitleId() {
        return titleId;
    }
    public String getLabel() {
        return label;
    }
    public void initialize() {

    }

}

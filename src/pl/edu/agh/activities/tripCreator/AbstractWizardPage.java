package pl.edu.agh.activities.tripCreator;

/**
 * Created by Sławek on 2014-12-09.
 */
public abstract class AbstractWizardPage {
    private int layoutId;
    private String name;

    public AbstractWizardPage(int layoutId, String name) {
        this.layoutId = layoutId;
        this.name = name;
    }

    public int getPageLayoutId() {
        return layoutId;
    }
    public String getPageName() {
        return name;
    }
}

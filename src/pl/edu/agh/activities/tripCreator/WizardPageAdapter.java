package pl.edu.agh.activities.tripCreator;

import android.content.Context;
import android.view.View;
import pl.edu.agh.fragments.AbstractAdapter;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-09.
 */
public class WizardPageAdapter  {

    private ArrayList<AbstractWizardPage> wizardPagesList;

    public WizardPageAdapter(Context context, ArrayList<AbstractWizardPage> pages) {
        //super(context, layoutId, items);
        wizardPagesList = new ArrayList<>();
        this.wizardPagesList.addAll(pages);
    }

}

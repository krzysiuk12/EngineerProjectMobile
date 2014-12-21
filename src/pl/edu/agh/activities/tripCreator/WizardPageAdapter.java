package pl.edu.agh.activities.tripcreator;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.common.BaseObject;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-09.
 */
public class WizardPageAdapter extends AbstractAdapter<TripCreatorWizardElement> {


    public WizardPageAdapter(Context context, ArrayList<TripCreatorWizardElement> pages) {
        super(context, R.layout.list_item, pages);
    }

    @Override
    protected void setLabels(View view, TripCreatorWizardElement item) {

    }
}

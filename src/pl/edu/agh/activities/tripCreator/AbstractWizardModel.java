package pl.edu.agh.activities.tripCreator;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import pl.edu.agh.fragments.AbstractAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Sławek on 2014-12-09.
 */
public abstract class AbstractWizardModel extends Fragment {

    protected Context context;
    protected static final String KEY_CURRENT_PAGE_INDEX = "currentPageIndex";
    protected int currentPageIndex = 0;

    private List<AbstractWizardPage> wizardPagesList;

    public AbstractWizardModel(Context context) {
        this.context = context;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            currentPageIndex = savedInstanceState.getInt(KEY_CURRENT_PAGE_INDEX, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE_INDEX, currentPageIndex);
    }

    protected abstract WizardPageAdapter getAdapterInstance();

    protected abstract AbstractWizardPage getWizardPageFragmentInstance(AbstractWizardPage page, int index);

//    protected void showPage(int index) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), getWizardPageFragment())
//    }

}

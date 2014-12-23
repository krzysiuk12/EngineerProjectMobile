package pl.edu.agh.activities.tripcreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardPageFragment extends AbstractWizardPage<TripCreatorWizardElement> {

    private TripCreatorWizardElement displayedWizardElement;

    public static TripCreatorWizardPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorWizardPageFragment fragment = new TripCreatorWizardPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }

//    public static TripCreatorWizardPageFragment newInstance(TripCreatorWizardElement wizardElement, long index, TripCreatorWizardPageFragment fragment {
//        if (fragment instanceof TripCreatorInitPageFragment) {
//            fragment = new TripCreatorInitPageFragment();
//            fragment.setInitialArguments(index, wizardElement);
//        }
//        return fragment;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        displayedWizardElement = (TripCreatorWizardElement) getArguments().getSerializable(KEY_ITEM);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return displayedWizardElement.getLayoutId();
    }

    @Override
    protected void showWizardPage() {

    }
}

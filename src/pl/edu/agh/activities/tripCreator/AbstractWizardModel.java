package pl.edu.agh.activities.tripCreator;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import pl.edu.agh.fragments.AbstractAdapter;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-12-09.
 */
public abstract class AbstractWizardModel<T extends Serializable> extends ListFragment {

    protected Context context;
    protected static final String KEY_CURRENT_PAGE_INDEX = "currentPageIndex";
    protected int currentPageIndex = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View wizardPageView = getActivity().findViewById(getWizardPageId());
        setListAdapter(getAdapterInstance());


        if(savedInstanceState != null) {
            currentPageIndex = savedInstanceState.getInt(KEY_CURRENT_PAGE_INDEX, 0);
        }

        currentPageIndex = 0;
        showWizardPage(currentPageIndex);
    }

    @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getListView().setItemChecked(position, true);
        showWizardPage(position);
    }

    public abstract void goNextPage(int position);

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE_INDEX, currentPageIndex);
    }

    protected void showWizardPage(int index) {

        currentPageIndex = index;

        Intent intent = new Intent();
        intent.setClass(getActivity(), getClassForWizardPageIntent(index));
        intent.putExtra(pl.edu.agh.activities.tripCreator.AbstractWizardPage.KEY_INDEX, getListAdapter().getItemId(currentPageIndex));
        intent.putExtra(pl.edu.agh.activities.tripCreator.AbstractWizardPage.KEY_ITEM, (Serializable) getListAdapter().getItem(currentPageIndex));
        startActivity(intent);


//        getListView().setItemChecked(index, true);
//
//        AbstractWizardPage wizardPageFragment = (AbstractWizardPage)
//                getFragmentManager().findFragmentById(getWizardPageId());
//
//        if ( wizardPageFragment == null || wizardPageFragment.getDisplayedWizardPageIndex() != index ) {
//            wizardPageFragment = getWizardPageFragmentInstance((T) getListAdapter().getItem(index), index);
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(getWizardPageId(), wizardPageFragment);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.commit();
//        }
    }

    protected abstract AbstractAdapter getAdapterInstance();

    protected abstract int getWizardPageId();

    protected abstract AbstractWizardPage getWizardPageFragmentInstance(T listItem, int index);

    protected abstract Class getClassForWizardPageIntent(int index);

}

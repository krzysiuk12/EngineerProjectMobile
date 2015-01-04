package pl.edu.agh.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.activities.settings.SettingsIssue;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.layout.toast.InfoToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssuePanelFragment extends AbstractDescriptionFragment<SettingsIssue> {

    private SettingsIssue displayedSettingsIssue;

    private IUserAccountManagementService userAccountManagementService;

    // <editor-fold description="Description fragment methods">

    public static SettingsIssuePanelFragment newInstance(SettingsIssue settingsIssue, long index) {
        SettingsIssuePanelFragment fragment = new SettingsIssuePanelFragment();
        fragment.setInitialArguments(index, settingsIssue);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        displayedSettingsIssue = (SettingsIssue) getArguments().getSerializable(KEY_ITEM);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        displayedSettingsIssue.setFragment(this);
        displayedSettingsIssue.initializeView(view);
        return view;
    }

    @Override
    protected int getLayoutId() {
        return displayedSettingsIssue.getLayoutId();
    }

    @Override
    protected void showDetails() {
    }

    // </editor-fold>

    // <editor-fold description="Setting-specific methods">

    public IUserAccountManagementService getUserAccountManagementService() {
        if ( userAccountManagementService == null ) {
            userAccountManagementService = new UserAccountManagementService(getActivity());
        }
        return userAccountManagementService;
    }

    public void showSuccessToastAndFinish() {
        showSuccessToast(R.string.Setting_Success);
		getActivity().finish();
    }

    public void showSuccessToast(int resourceId) {
        new InfoToastBuilder(this.getActivity(), getString(resourceId)).build().show();
    }

    public void showErrorToast(int resourceId) {
        new ErrorToastBuilder(this.getActivity(), getString(resourceId)).build().show();
    }

    // </editor-fold>
}
package pl.edu.agh.activities.tripcreator;

import android.app.Activity;
import android.os.Bundle;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;

/**
 * Created by Sławek on 2014-12-13.
 */
public abstract class AbstractWizardPageActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ( findViewById(getWizardPageId()) != null ) {
            finish();
            return;
        }

        if(savedInstanceState == null) {
            AbstractWizardPage wizardPage = getWizardPageFragment(0);
            wizardPage.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, wizardPage).commit();
        }

    }

    protected abstract int getWizardPageId();
    //protected abstract AbstractWizardPage getWizardPageFragment();

    protected abstract AbstractWizardPage getWizardPageFragment(int pageIndex);

}

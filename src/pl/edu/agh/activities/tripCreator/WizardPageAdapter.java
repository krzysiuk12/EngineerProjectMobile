package pl.edu.agh.activities.tripcreator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.domain.common.BaseObject;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-09.
 */
public class WizardPageAdapter extends AbstractAdapter<TripCreatorWizardElement> {

    private ArrayList<TripCreatorWizardElement> wizardPages = new ArrayList<TripCreatorWizardElement>();

    public WizardPageAdapter(Context context, ArrayList<TripCreatorWizardElement> pages) {
        super(context, R.layout.list_item, pages);
        wizardPages.addAll(pages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }
        TripCreatorWizardElement item = getItem(position);
        setLabels(convertView, item);
        setTextColor(convertView, position);
        return convertView;
    }

    @Override
    protected void setLabels(View view, TripCreatorWizardElement wizardElement) {
        RenderingTools.setTextViewText(view, R.id.ListItem_Name, wizardElement.getLabel());
    }

    public void setTextColor(View view, int position) {
        if(!isEnabled(position)){
            RenderingTools.setTextViewColor(view, R.id.ListItem_Name, Color.GRAY);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if(wizardPages.get(position).getIsEnabled()){
            return true;
        }
        return false;
    }

}

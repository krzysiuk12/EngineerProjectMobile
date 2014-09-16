package pl.edu.agh.layout.toast;

import android.app.Activity;
import pl.edu.agh.main.R;

/**
 * Created by Krzysiu on 2014-09-13.
 */
public class WarningToastBuilder extends ToastBuilder {

    public WarningToastBuilder(Activity activity, String message) {
        super(activity, message);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.warning_toast;
    }

    @Override
    protected int getLayoutRootElementId() {
        return R.id.Toast_Root;
    }

    @Override
    protected int getImageResource() {
        return R.drawable.warning_icon;
    }

    @Override
    protected int getImageViewResourceId() {
        return R.id.Toast_Image;
    }

    @Override
    protected int getTextViewResourceId() {
        return R.id.Toast_Message;
    }

}

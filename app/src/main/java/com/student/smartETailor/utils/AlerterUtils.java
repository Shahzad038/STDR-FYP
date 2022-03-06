package com.student.smartETailor.utils;

import android.app.Activity;
import android.view.View;

import com.student.smartETailor.R;
import com.tapadoo.alerter.Alerter;

public class AlerterUtils {
    private final String TAG = AlerterUtils.class.getSimpleName();

    private static AlerterUtils _INSTANCE;

    private AlerterUtils() {
    }

    public static AlerterUtils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new AlerterUtils();
        }
        return _INSTANCE;
    }

    public void showAlertForConnectivity(Activity activity) {
        showAlert(activity, "Connectivity", "Please connect to internet and then try again!", null, R.color.primary_dark, 3000);
    }

    public void showAlert(Activity activity, String title, String msg, View.OnClickListener paramOnClickListener, int backgoundColor, int duration) {
        Alerter localAlerter = Alerter.create(activity)
                .setTitle(title)
                .setText(msg)
                .setBackgroundColorRes(backgoundColor)
                .setDuration(duration);
        if (paramOnClickListener != null) {
            localAlerter.setOnClickListener(paramOnClickListener).show();
            return;
        }
        localAlerter.show();
    }

    public void showModifiedAlert(Activity activity, String title, String msg) {
        Alerter localAlerter = Alerter.create(activity)
                .setTitle(title)
                .setText(msg)
                .setBackgroundColorRes(R.color.primary_dark)
                .setDuration(3000);
        localAlerter.show();
    }

    public void showAlertForCreateAccountError(Activity activity) {
        showModifiedAlert(activity, "Account error", "Error in account creation");
    }

    public void notImplemented(Activity activity) {
        showModifiedAlert(activity, "Coming soon...", "This feature is not available yet.");
    }


    public void showAlertForError(Activity activity) {
        showAlert(activity, "Error!", "Something went wrong. Please try again!", null, R.color.primary_dark, 3000);
    }

    public void showAlertForProfilePicNotUpload(Activity activity) {
        showModifiedAlert(activity, "Profile picture", "Profile pic not uploaded");
    }


}

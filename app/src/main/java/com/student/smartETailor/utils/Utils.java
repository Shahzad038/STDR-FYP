package com.student.smartETailor.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.student.smartETailor.R;
import com.student.smartETailor.ui.LoginActivity;


public class Utils {
    private final String TAG = Utils.class.getSimpleName();

    private static Utils _INSTANCE;

    private Utils() {
    }

    public static Utils getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new Utils();
        }
        return _INSTANCE;
    }




    public boolean isNetworkAvailable(Context context) {
        NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (localNetworkInfo != null) && (localNetworkInfo.isConnected());
    }

    public void signOut(Activity activity) {
        UsersUtils.getInstance(activity).signOut();
        FirebaseAuth.getInstance().signOut();
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    public void requestAllPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 500);
        }
    }

    public void rateApp(Activity activity) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

    public void shareApp(Activity activity) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "http://play.google.com/store/apps/details?id=" + activity.getPackageName());
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
        activity.startActivity(Intent.createChooser(shareIntent, "Share..."));
    }

}

package com.student.smartETailor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.student.smartETailor.models.User;


public class UsersUtils {
    private final String SHARED_PREF_NAME = "USERPREF";
    private final String SHARED_UID = "UID";
    private final String SHARED_USERNAME = "username";
    private final String SHARED_IMAGE = "imageURL";
    private final String SHARED_CONTACT = "contact";
    private final String SHARED_EMAIL = "email";
    private final String SHARED_ADDRESS = "address";
    private final String SHARED_GENDER = "gender";
    private final String SHARED_TYPE = "type";
    private final String SHARED_STATUS = "status";

    Context context;

    private UsersUtils(Context context) {
        this.context = context;
    }

    private static UsersUtils _INSTANCE;

    public static UsersUtils getInstance(Context context) {
        if (_INSTANCE == null) _INSTANCE = new UsersUtils(context);
        return _INSTANCE;
    }





    public void save(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_UID, user.getUID());

        editor.putString(SHARED_USERNAME, user.getName());
        editor.putString(SHARED_IMAGE, user.getImageURL());
        editor.putString(SHARED_CONTACT, user.getContact());
        editor.putString(SHARED_EMAIL, user.getEmail());
        editor.putString(SHARED_ADDRESS, user.getAddress());
        editor.putString(SHARED_GENDER, user.getGender());

        editor.putString(SHARED_TYPE, user.getType());
        editor.putString(SHARED_STATUS, user.getStatus());
        editor.apply();
    }

    public User fetchUser() {
        User userData = new User();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userData.setUID(sharedPreferences.getString(SHARED_UID, ""));

        userData.setName(sharedPreferences.getString(SHARED_USERNAME, ""));
        userData.setImageURL(sharedPreferences.getString(SHARED_IMAGE, ""));
        userData.setContact(sharedPreferences.getString(SHARED_CONTACT, ""));
        userData.setEmail(sharedPreferences.getString(SHARED_EMAIL, ""));
        userData.setAddress(sharedPreferences.getString(SHARED_ADDRESS, ""));
        userData.setGender(sharedPreferences.getString(SHARED_GENDER, ""));

        userData.setType(sharedPreferences.getString(SHARED_TYPE, ""));
        userData.setStatus(sharedPreferences.getString(SHARED_STATUS, ""));
        return userData;
    }

    public void signOut() {
        User user = new User();
        save(user);
    }

}

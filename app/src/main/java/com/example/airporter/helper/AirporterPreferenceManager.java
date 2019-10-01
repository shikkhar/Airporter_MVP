package com.example.airporter.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.airporter.AppController;

public class AirporterPreferenceManager {
    private AirporterPreferenceManager mSharedPrefernceManager;
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;
    private Context appContext;
    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "myPrefs";
    private static final String KEY_USER_ID = "user_id";


    public AirporterPreferenceManager(Context appContext) {
        this.appContext = appContext;
        mSharedPrefs = appContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPrefs.edit();
    }

    public void storeUserId(String userId){
        mEditor.putString(KEY_USER_ID, userId);
        mEditor.commit();
    }

    public String getUserId(){
        return mSharedPrefs.getString(KEY_USER_ID, null);
    }
}
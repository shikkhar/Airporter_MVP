package com.example.airporter;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.airporter.helper.AirporterPreferenceManager;

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private AirporterPreferenceManager mPreferenceManager;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static Context getContext(){

        return mInstance.getApplicationContext();
    }
    public synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            return mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        else
            return mRequestQueue;
    }

    public synchronized AirporterPreferenceManager getPreferenceManager(){
        if(mPreferenceManager == null)
            return mPreferenceManager = new AirporterPreferenceManager();
        else
            return mPreferenceManager;
    }
}

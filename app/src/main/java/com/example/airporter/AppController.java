package com.example.airporter;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.airporter.helper.AirporterPreferenceManager;
import com.example.airporter.helper.CONSTANTS;

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private AirporterPreferenceManager mPreferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CONSTANTS.Notifications.CHANNEL_ID, CONSTANTS.Notifications.CHANNEL_NAME, importance);
            mChannel.setDescription(CONSTANTS.Notifications.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            return mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        else
            return mRequestQueue;
    }

    public synchronized AirporterPreferenceManager getPreferenceManager(){
        if(mPreferenceManager == null)
            return mPreferenceManager = new AirporterPreferenceManager(mInstance.getApplicationContext());
        else
            return mPreferenceManager;
    }
}

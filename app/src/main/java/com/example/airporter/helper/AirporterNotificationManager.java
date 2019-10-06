package com.example.airporter.helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import com.example.airporter.MainActivity.MainActivity;
import com.example.airporter.R;


import static android.content.Context.NOTIFICATION_SERVICE;

public class AirporterNotificationManager {

    private Context appContext;
    private static AirporterNotificationManager mInstance;

    public AirporterNotificationManager(Context appContext) {
        this.appContext = appContext;
    }

    public static synchronized AirporterNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AirporterNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(appContext, CONSTANTS.Notifications.CHANNEL_ID)
                        .setSmallIcon(R.drawable.abc)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true);


        /*
         *  Clicking on the notification will take us to this intent
         *  Right now we are using the MainActivity as this is the only activity we have in our application
         *  But for your project you can customize it as you want
         * */

        Intent resultIntent = new Intent(appContext, MainActivity.class);

        /*
         *  Now we will create a pending intent
         *  The method getActivity is taking 4 parameters
         *  All parameters are describing themselves
         *  0 is the request code (the second parameter)
         *  We can detect this code in the activity that will open by this we can get
         *  Which notification opened the activity
         * */
        PendingIntent pendingIntent = PendingIntent.getActivity(appContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
         *  Setting the pending intent to notification builder
         * */

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) appContext.getSystemService(NOTIFICATION_SERVICE);

        /*
         * The first parameter is the notification id
         * better don't give a literal here (right now we are giving a int literal)
         * because using this id we can modify it later
         * */
        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}

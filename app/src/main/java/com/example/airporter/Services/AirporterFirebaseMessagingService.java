package com.example.airporter.Services;


import android.util.Log;

import com.android.volley.VolleyError;
import com.example.airporter.AppController;
import com.example.airporter.data.ChatMessage;
import com.example.airporter.data.Messages;
import com.example.airporter.helper.AirporterNotificationManager;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.*;

public class AirporterFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "fcm";
    public AirporterFirebaseMessagingService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        AppController.getInstance().getPreferenceManager().storeFcmToken(s);
        Log.d(TAG, "onNewToken: " + s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size()>0)
        {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String messageText = remoteMessage.getData().get("chatMessage");
            String orderId = remoteMessage.getData().get("orderId");
            String bidderId = remoteMessage.getData().get("bidderId");
            String shopperId = remoteMessage.getData().get("shopperId");
            String bidderName = remoteMessage.getData().get("bidderName");
            String shopperName = remoteMessage.getData().get("shopperName");
            String senderId = remoteMessage.getData().get("senderId");

            ChatMessage chatMessage = new ChatMessage(messageText, senderId);
            EventBus.getDefault().post(chatMessage);

            Messages message = new Messages(orderId, bidderId, shopperId, bidderName, shopperName);
            /*Intent intent = new Intent(CONSTANTS.PUSH_MESSAGE);
            intent.putExtra("chatMessage", chatMessage);
            intent.putExtra("bidderId", bidderId);
            intent.putExtra("shopperId", shopperId);
            intent.putExtra("senderId", senderId);

            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);*/

            AirporterNotificationManager.getInstance(getApplicationContext()).displayNotification(title,body, message);

        }
    }

}

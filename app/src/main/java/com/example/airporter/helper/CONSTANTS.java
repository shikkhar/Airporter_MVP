package com.example.airporter.helper;

import android.content.Context;
import android.content.res.Resources;

import com.example.airporter.AppController;
import com.example.airporter.R;

public class CONSTANTS {
    private static Context applicationContext = AppController.getInstance().getApplicationContext();

    public static final String IpAddress = "https://www.airporterinc.com/android_scripts/";

    public static class Notifications{
        public static final String CHANNEL_ID = "Channel_Airporter_Id";
        public static final String CHANNEL_NAME = "Channel Name";
        public static final String CHANNEL_DESCRIPTION = "Channel Description";
    }


    public static class NetworRequestResponse{
        public static final String SUCCESS = "1";
        public static final String FAIL ="0";
    }

    public static class NetworkRequestTags{
        public static final String LOGIN_WITH_EMAIL = "0";
        public static final String FETCH_ORDER_LIST ="1";
        public static final String SUBMIT_OFFER ="2";
        public static final String FETCH_OFFERS_RECEIVED ="3";
        public static final String FETCH_OFFER_DETAILS ="4";
        public static final String ACCEPT_OFFER ="5";
        public static final String REJECT_OFFER ="6";
        public static final String REJECT_OTHER__OFFERS ="7";
        public static final String UPDATE_PORTER ="8";
        public static final String FETCH_OFFERS_SENT ="9";
        public static final String WITHDRAW_OFFER ="10";
        public static final String UPDATE_OFFER ="11";
        public static final String FETCH_MESSAGES ="12";
        public static final String FETCH_CHAT_MESSAGES ="13";
        public static final String UPLOAD_CHAT_MESSAGE ="14";
        public static final String STORE_FCM_TOKEN ="15";
        public static final String FETCH_FCM_TOKEN ="16";
        public static final String SEND_CHAT_MESSAGE ="17";
        public static final String FETCH_REQUESTED_ORDERS ="18";
        public static final String FETCH_IN_TRANSIT_ORDERS ="19";
        public static final String FETCH_RECEIVED_ORDERS ="20";
        public static final String FETCH_DELIVERED_ORDERS ="21";

    }

    public static class DisplayMessages{
        public static final String ERROR_EMAIL_EMPTY = "Email field cannot be left empty"  ;
        public static final String ERROR_EMAIL_INVALID = "Invalid Email" ;
        public static final String ERROR_PASSWORD_EMPTY = "Password field cannot be left empty";
    }
}

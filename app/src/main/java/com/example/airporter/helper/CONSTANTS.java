package com.example.airporter.helper;

import android.content.Context;
import android.content.res.Resources;

import com.example.airporter.AppController;
import com.example.airporter.R;

public class CONSTANTS {
    private static Context applicationContext = AppController.getInstance().getApplicationContext();

    public static final String IpAddress = "https://www.airporterinc.com/android_scripts/";

    public static class NetworRequestResponse{
        public static final String SUCCESS = "1";
        public static final String FAIL ="0";
    }

    public static class NetworkRequestTags{
        public static final String LOGIN_WITH_EMAIL = "0";
        public static final String FETCH_ORDER_LIST ="1";
    }

    public static class DisplayMessages{
        public static final String ERROR_EMAIL_EMPTY = "Email field cannot be left empty"  ;
        public static final String ERROR_EMAIL_INVALID = "Invalid Email" ;
        public static final String ERROR_PASSWORD_EMPTY = "Password field cannot be left empty";
    }
}

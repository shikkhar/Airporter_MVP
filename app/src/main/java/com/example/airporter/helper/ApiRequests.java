package com.example.airporter.helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.airporter.AppController;
import com.example.airporter.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ApiRequests {
    private static ApiRequests mInstance;

    private ApiRequests() {
    }

    public static synchronized ApiRequests getInstance() {
        if (mInstance == null)
            return mInstance = new ApiRequests();
        else
            return mInstance;
    }

    public void authenticateLogin(String email, String encryptedPassword, VolleySeverRequest.VolleyResponseCallback responseCallback) {

        String url = CONSTANTS.IpAddress + "authenticate_login.php";
        HashMap<String, String> params = new HashMap();
        params.put("username", email);
        params.put("password", encryptedPassword);
        VolleySeverRequest severRequest = new VolleySeverRequest(responseCallback);
        severRequest.makePostRequest(url, params);

    }

    public void fetchOrderList(VolleySeverRequest.VolleyResponseCallback responseCallback) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_orders.php";
        HashMap<String, String> params = new HashMap();
        //orders which have not been picked up by any user till now
        params.put("orderPicked","0");
        params.put("userId", userId);
        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params);
    }
}

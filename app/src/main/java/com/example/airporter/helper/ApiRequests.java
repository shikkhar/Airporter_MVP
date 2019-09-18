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

    public static synchronized ApiRequests getInstance(){
        if(mInstance == null)
            return mInstance = new ApiRequests();
        else
            return mInstance;
    }

    public void authenticateLogin(String email, String encryptedPassword, VolleySeverRequest.VolleyResponseCallback responseCallback){

        String url = CONSTANTS.IpAddress +"authenticate_login.php";
        HashMap<String, String> params = new HashMap();
        params.put("username", email );
        params.put("password", encryptedPassword );
        VolleySeverRequest severRequest = new VolleySeverRequest(responseCallback);
        severRequest.makePostRequest(url, params);

    }
}

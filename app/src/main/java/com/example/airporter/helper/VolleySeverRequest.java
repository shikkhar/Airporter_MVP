package com.example.airporter.helper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.airporter.AppController;

import org.json.JSONObject;

import java.util.HashMap;

public class VolleySeverRequest {
    private VolleyResponseCallback volleyResponseCallback;

    public VolleySeverRequest(VolleyResponseCallback volleyResponseCallback) {
        this.volleyResponseCallback = volleyResponseCallback;
    }

    public void makePostRequest(String url, HashMap params){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        volleyResponseCallback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        volleyResponseCallback.onFail(error);
                    }
                });

        AppController.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    public interface VolleyResponseCallback{
        void onSuccess(JSONObject response);
        void onFail(VolleyError error);
    }
}

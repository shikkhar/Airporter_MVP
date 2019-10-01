package com.example.airporter.helper;

import com.example.airporter.AppController;

import java.util.HashMap;

public class ApiRequestManager {
    private static ApiRequestManager mInstance;

    private ApiRequestManager() {
    }

    public static synchronized ApiRequestManager getInstance() {
        if (mInstance == null)
            return mInstance = new ApiRequestManager();
        else
            return mInstance;
    }

    public void authenticateLogin(String email, String encryptedPassword, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {

        String url = CONSTANTS.IpAddress + "authenticate_login.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("username", email);
        params.put("password", encryptedPassword);
        VolleySeverRequest severRequest = new VolleySeverRequest(responseCallback);
        severRequest.makePostRequest(url, params, requestTag);

    }

    public void fetchOrderList(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_orders.php";
        HashMap<String, String> params = new HashMap<>();
        //orders which have not been picked up by any user till now
        params.put("orderPicked","0");
        params.put("userId", userId);
        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchMoreOrders(String lastOrderIdFetched, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_moreOrders.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",lastOrderIdFetched);
        params.put("orderPicked","0");
        params.put("userId", userId);
        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

}

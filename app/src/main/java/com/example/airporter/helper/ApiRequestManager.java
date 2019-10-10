package com.example.airporter.helper;

import com.example.airporter.AppController;
import com.example.airporter.data.Messages;
import com.example.airporter.data.Order;

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

    public void submitOffer(String orderId, String offerPrice, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){
        //TODO: before submitting the offer i need to check if the order is still available
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress +"put_offer.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("offerPrice", offerPrice);
        params.put("bidderId", userId);
        params.put("offerAccepted","0");
        params.put("offerRejected","0");

        VolleySeverRequest severRequest = new VolleySeverRequest(responseCallback);
        severRequest.makePostRequest(url, params, requestTag);

    }

    public void fetchOffersReceived(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_offersReceived.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("offerAccepted","0");
        params.put("offerRejected","0");
        params.put("offerCancelled", "0");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchOfferDetails(String orderId, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress +"get_offerDetails.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("offerAccepted","0");
        params.put("offerRejected","0");
        params.put("offerCancelled","0");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void acceptOffer(String orderId, String offerId ,VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress+"acceptOffer.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("offerId",offerId);
        params.put("offerAccepted","1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void rejectOffer(String orderId, String offerId ,VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress+"rejectOffer.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("offerId",offerId);
        params.put("offerRejected","1");


        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void rejectOtherOffers(String orderId, String offerId ,VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress+"rejectOtherOffers.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("offerId",offerId);
        params.put("offerRejected","1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void updatePorterId(String orderId, String bidderId ,VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress+"updatePorterId.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("porterId",bidderId);
        params.put("orderPicked","1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchOffersSent(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress +"get_offerSent.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void withdrawOffer(String offerId, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress + "withdrawOffer.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("offerId", offerId);
        params.put("offerWithdrawn", "1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void updateOffer(String offerId, String offerPrice, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress+"modifyOffer.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("offerId", offerId);
        params.put("offerPrice", offerPrice);
        params.put("offerAccepted", "0");
        params.put("offerRejected", "0");
        params.put("offerWithdrawn", "0");
        params.put("orderPicked", "1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchMessages(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress +"get_orderMessages.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchChatMessages(String orderId, String bidderId, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress +"get_chatMessages.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId",orderId);
        params.put("bidderId",bidderId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void uploadChatMessage(String orderId, String messageToSend, String bidderId, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress + "put_chatMessage.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("chatMessage", messageToSend);
        params.put("bidderId", bidderId);
        params.put("senderId", userId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void storeFcmToken(String token, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress +"put_FCMToken.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("token", token);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchFcmToken(String receiverId, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String url = CONSTANTS.IpAddress + "get_FCMToken.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",receiverId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void sendChatMessage(String fcmToken, String senderName, String messageToSend, Messages message, VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag){

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress + "fcm_test.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("token",fcmToken);
        params.put("title","New Message");
        params.put("body", senderName + " has messaged you");
        params.put("chatMessage",messageToSend);
        params.put("orderId", message.getOrderId());
        params.put("bidderId", message. getBidderId());
        params.put("shopperId", message.getShopperId());
        params.put("bidderName", message.getBidderName());
        params.put("shopperName", message.getShopperName());
        params.put("senderId", userId);

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchRequestedOrders(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_requestOrders.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("userId",userId);
        params.put("orderPicked", "0");
        params.put("orderReceived", "0");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchInTransitOrders(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_inTransitOrders.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("userId",userId);
        params.put("orderPicked","1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchReceivedOrders(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_receivedOrders.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("userId",userId);
        params.put("orderReceived","1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }

    public void fetchDeliveredOrders(VolleySeverRequest.VolleyResponseCallback responseCallback, String requestTag) {
        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String url = CONSTANTS.IpAddress+"get_deliveredOrders.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("userId",userId);
        params.put("orderDelivered", "1");

        VolleySeverRequest serverRequest = new VolleySeverRequest(responseCallback);
        serverRequest.makePostRequest(url, params, requestTag);
    }
}

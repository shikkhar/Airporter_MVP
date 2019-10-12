package com.example.airporter.MenuModule.MessagesFragmentModule.ChatMessagesModule;

import com.android.volley.VolleyError;
import com.example.airporter.data.ChatMessage;
import com.example.airporter.data.Messages;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.FETCH_CHAT_MESSAGES;
import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.FETCH_FCM_TOKEN;
import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.SEND_CHAT_MESSAGE;
import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.UPLOAD_CHAT_MESSAGE;

public class ChatMessagesPresenter implements ChatMessagesMvpContract {
    private ChatMessagesView mView;
    private ApiRequestManager apiRequestObject;
    private String userId;

    public ChatMessagesPresenter(ChatMessagesView mView, ApiRequestManager apiRequestObject, String userId) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
        this.userId = userId;
    }

    @Override
    public void fetchChatMessages(String orderId, String bidderId) {
        apiRequestObject.fetchChatMessages(orderId, bidderId, new FetchChatMessagesCallback(mView), FETCH_CHAT_MESSAGES);
    }

    @Override
    public void uploadChatMessage(String orderId, String messageToSend, String bidderId) {
        if (!messageToSend.trim().isEmpty()) {
            apiRequestObject.uploadChatMessage(orderId, messageToSend, bidderId, new UploadChatMessageCallback(mView, messageToSend, userId), UPLOAD_CHAT_MESSAGE);
        }
    }

    @Override
    public void fetchFCMToken(String receiverId) {
        apiRequestObject.fetchFcmToken(receiverId, new FetchFcmTokenCallback(mView), FETCH_FCM_TOKEN);
    }

    @Override
    public void sendChatMessage(String fcmToken, String senderName, String messageToSend, Messages message) {
        apiRequestObject.sendChatMessage(fcmToken, senderName, messageToSend, message, new SendChatMessageCallback(mView), SEND_CHAT_MESSAGE);
    }

    private static class FetchChatMessagesCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<ChatMessagesView> mView;
        private ArrayList<ChatMessage> chatMessageList = new ArrayList<>();

        public FetchChatMessagesCallback(ChatMessagesView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                ChatMessagesView view = mView.get();
                if (view != null) {
                    view.onChatMessagesFetched(chatMessageList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            ChatMessagesView view = mView.get();
            if (view != null) {

            }
        }

        private void parseJsonResponse(JSONObject response) throws JSONException {
            JSONArray dataArray = response.getJSONArray("data");

            for (int x = 0; x < dataArray.length(); x++) {
                String message = dataArray.getJSONObject(x).getString("chatMessage");
                String senderId = dataArray.getJSONObject(x).getString("senderId");

                //messageType = dataArray.getJSONObject(x).getString("messageType");
                //TODO: remove bidderId and shopperId from ChatMessage Object if not needed
                ChatMessage chatMessage = new ChatMessage(message, senderId);
                chatMessageList.add(chatMessage);
            }
        }
    }

    private static class UploadChatMessageCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<ChatMessagesView> mView;
        private String messageToSend;
        private String userId;
        private ChatMessage chatMessage;

        public UploadChatMessageCallback(ChatMessagesView view, String messageToSend, String userId) {
            this.mView = new WeakReference<>(view);
            this.messageToSend = messageToSend;
            this.userId = userId;

        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                ChatMessagesView view = mView.get();
                if (view != null) {
                    view.onChatMessageUploaded(chatMessage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            ChatMessagesView view = mView.get();
            if (view != null) {

            }
        }

        private void parseJsonResponse(JSONObject response) throws JSONException {

            String success = response.getString("success");
            if (success.equals(CONSTANTS.NetworRequestResponse.SUCCESS)) {
                chatMessage = new ChatMessage(messageToSend, userId);
            }
        }
    }

    private static class FetchFcmTokenCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<ChatMessagesView> mView;
        private String token;

        public FetchFcmTokenCallback(ChatMessagesView view) {
            this.mView = new WeakReference<>(view);


        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                ChatMessagesView view = mView.get();
                if (view != null) {
                    view.onFcmTokenFetched(token);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            ChatMessagesView view = mView.get();
            if (view != null) {

            }
        }

        private void parseJsonResponse(JSONObject response) throws JSONException {
            JSONArray data = response.getJSONArray("data");
            token = data.getJSONObject(0).getString("token");
        }
    }

    private static class SendChatMessageCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<ChatMessagesView> mView;

        public SendChatMessageCallback(ChatMessagesView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                ChatMessagesView view = mView.get();
                if (view != null) {
                    view.onChatMessageSent();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            ChatMessagesView view = mView.get();
            if (view != null) {

            }
        }

        private void parseJsonResponse(JSONObject response) throws JSONException {

        }
    }
}

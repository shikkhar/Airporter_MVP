package com.example.airporter.MenuModule.MessagesFragment;

import com.android.volley.VolleyError;
import com.example.airporter.data.Messages;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MessagesPresenter implements MessagesMvpContract {
    MessagesFragment mView;
    ApiRequestManager apiRequestObject;

    public MessagesPresenter(MessagesFragment mView, ApiRequestManager apiRequestObject) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
    }

    @Override
    public void fetchMessages() {
        apiRequestObject.fetchMessages(new FetchMessagesCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_MESSAGES);
    }

    private static class FetchMessagesCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<MessagesView> mView;
        private ArrayList<Messages> messagesList;

        public FetchMessagesCallback(MessagesView view) {
            this.mView = new WeakReference<>(view);
            this.messagesList = new ArrayList<>();
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                MessagesView view = mView.get();
                if (view != null)
                    view.onMessagesFetched(messagesList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onFail(VolleyError error) {

        }

        private void parseJsonResponse(JSONObject response) throws JSONException {
            JSONArray dataArray = response.getJSONArray("data");

            for (int x = 0; x < dataArray.length(); x++) {
                String orderId = dataArray.getJSONObject(x).getString("orderId");
                String bidderId = dataArray.getJSONObject(x).getString("bidderId");
                String shopperId = dataArray.getJSONObject(x).getString("shopperId");
                String bidderName = dataArray.getJSONObject(x).getString("bidderFirstName") + " " + dataArray.getJSONObject(x).getString("bidderLastName");
                String shopperName = dataArray.getJSONObject(x).getString("shopperFirstName") + " " + dataArray.getJSONObject(x).getString("shopperLastName");


                Messages message = new Messages(orderId, bidderId, shopperId, bidderName, shopperName);
                messagesList.add(message);
            }
        }
    }
}

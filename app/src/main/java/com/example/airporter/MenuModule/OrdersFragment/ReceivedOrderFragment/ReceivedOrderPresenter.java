package com.example.airporter.MenuModule.OrdersFragment.ReceivedOrderFragment;

import com.android.volley.VolleyError;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ReceivedOrderPresenter implements ReceivedOrderMvpContract {
    ReceivedOrderView mView;
    ApiRequestManager apiRequestObject;

    public ReceivedOrderPresenter(ReceivedOrderView mView, ApiRequestManager apiRequestObject) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
    }

    @Override
    public void fetchReceivedOrders() {
        apiRequestObject.fetchReceivedOrders(new FetchReceivedOrdersCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_RECEIVED_ORDERS);
    }

    private static class FetchReceivedOrdersCallback implements VolleySeverRequest.VolleyResponseCallback {

        private WeakReference<ReceivedOrderView> mView;
        private ArrayList<Order> receivedOrderList = new ArrayList<>();

        public FetchReceivedOrdersCallback(ReceivedOrderView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                ReceivedOrderView view = mView.get();
                if (view != null) {
                    view.onReceivedOrdersFetched(receivedOrderList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {

        }

        private void parseJsonResponse(JSONObject response) throws JSONException {
            JSONArray orderArray = response.getJSONArray("data");

            for (int x = 0; x < orderArray.length(); x++) {
                String orderId = orderArray.getJSONObject(x).getString("orderId");
                String shopperName = orderArray.getJSONObject(x).getString("firstName") + " " + orderArray.getJSONObject(x).getString("lastName");
                String productName = orderArray.getJSONObject(x).getString("productTitle");
                String deliverFrom = orderArray.getJSONObject(x).getString("deliverFrom");
                String deliverTo = orderArray.getJSONObject(x).getString("deliverTo");
                String deliverBefore = orderArray.getJSONObject(x).getString("deliverBefore");
                String price = orderArray.getJSONObject(x).getString("price");
                String orderImage = "http://admin.airporterinc.com" + orderArray.getJSONObject(x).getString("imagePath");
                String orderDateTime = orderArray.getJSONObject(x).getString("orderDateTime");

                Order order = new Order(orderId, shopperName, productName, deliverFrom, deliverTo, deliverBefore, price, orderImage, orderDateTime, orderImage);
                receivedOrderList.add(order);
            }
        }
    }
}

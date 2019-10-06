package com.example.airporter.Menu.OrdersFragment.InTransitOrderFragment;

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

public class InTransitOrderPresenter implements InTransitOrderMvpContract {
    InTransitOrderView mView;
    ApiRequestManager apiRequestObject;

    public InTransitOrderPresenter(InTransitOrderView mView, ApiRequestManager apiRequestObject) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
    }


    @Override
    public void fetchInTransitOrders() {
        apiRequestObject.fetchInTransitOrders(new FetchTransitOrdersCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_IN_TRANSIT_ORDERS);
    }

    private static class FetchTransitOrdersCallback implements VolleySeverRequest.VolleyResponseCallback {

        private WeakReference<InTransitOrderView> mView;
        private ArrayList<Order> inTransitOrderList = new ArrayList<>();

        public FetchTransitOrdersCallback(InTransitOrderView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
                InTransitOrderView view = mView.get();
                if (view != null) {
                    view.onTransitOrdersFetched(inTransitOrderList);
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
                inTransitOrderList.add(order);
            }
        }
    }
}

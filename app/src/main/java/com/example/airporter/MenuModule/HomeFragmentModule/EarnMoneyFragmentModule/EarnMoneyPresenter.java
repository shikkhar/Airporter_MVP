package com.example.airporter.MenuModule.HomeFragmentModule.EarnMoneyFragmentModule;

import com.android.volley.VolleyError;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarnMoneyPresenter implements EarnMoneyContract {
    private EarnMoneyView mView;
    private ApiRequestManager apiRequestManagerObject;

    public EarnMoneyPresenter(EarnMoneyView mView, ApiRequestManager apiRequestManagerObject) {
        this.mView = mView;
        this.apiRequestManagerObject = apiRequestManagerObject;
    }

    @Override
    public void fetchOrderList() {
        apiRequestManagerObject.fetchOrderList(new OrderListFetchedCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_ORDER_LIST);
    }

    @Override
    public void fetchMoreOrders(String lastOrderIdFetched) {
        apiRequestManagerObject.fetchMoreOrders(lastOrderIdFetched, new OrderListFetchedCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_ORDER_LIST);
    }

    @Override
    public void submitOffer(String orderId, String offerPrice) {
        apiRequestManagerObject.submitOffer(orderId, offerPrice, new OnOfferSubmitCallback(mView), CONSTANTS.NetworkRequestTags.SUBMIT_OFFER);

    }

    private static class OnOfferSubmitCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<EarnMoneyView> mView;

        public OnOfferSubmitCallback(EarnMoneyView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            EarnMoneyView view = mView.get();

            if (view != null) {
                view.onOfferSubmitted(CONSTANTS.NetworRequestResponse.SUCCESS);
            }
        }

        @Override
        public void onFail(VolleyError error) {
            EarnMoneyView view = mView.get();

            if (view != null) {
                view.onOfferSubmitted(CONSTANTS.NetworRequestResponse.FAIL);
            }
        }
    }

    private static class OrderListFetchedCallback implements VolleySeverRequest.VolleyResponseCallback {
        private List<Order> orderList = new ArrayList<>();
        private WeakReference<EarnMoneyView> mView;

        public OrderListFetchedCallback(EarnMoneyView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJSONResult(response);
                EarnMoneyView view = mView.get();
                if (view != null)
                    view.onOrderListFetched(orderList);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private void parseJSONResult(JSONObject response) throws JSONException, ParseException {
            JSONArray orderArray = response.getJSONArray("data");
            for (int x = 0; x < orderArray.length(); x++) {
                String orderId = orderArray.getJSONObject(x).getString("orderId");
                String shopperName = orderArray.getJSONObject(x).getString("firstName") + " " + orderArray.getJSONObject(x).getString("lastName");
                String productName = orderArray.getJSONObject(x).getString("productTitle");
                String deliverFrom = orderArray.getJSONObject(x).getString("deliverFrom");
                String deliverTo = orderArray.getJSONObject(x).getString("deliverTo");
                String deliverBefore = orderArray.getJSONObject(x).getString("deliverBefore");
                String price = orderArray.getJSONObject(x).getString("price");
                String productImagePath = "http://admin.airporterinc.com" + orderArray.getJSONObject(x).getString("productImagePath");
                String orderDateTime = orderArray.getJSONObject(x).getString("orderDateTime");
                String shopperImagePath = "http://airporterinc.com" + orderArray.getJSONObject(x).getString("shopperImagePath");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(orderDateTime);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                //lastOrderIdDisplayed = orderId;
                Order order = new Order(orderId, shopperName, productName, deliverFrom, deliverTo, deliverBefore, price, productImagePath, orderDateTime, shopperImagePath);
                orderList.add(order);
            }
        }

        @Override
        public void onFail(VolleyError error) {
            // Toast.makeText(appContext, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

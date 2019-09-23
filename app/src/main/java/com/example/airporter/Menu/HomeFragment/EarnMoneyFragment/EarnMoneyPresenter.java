package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;

import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.airporter.AppController;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequests;
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

    public EarnMoneyPresenter(EarnMoneyView mView) {
        this.mView = mView;
    }

    @Override
    public void fetchOrderList(ApiRequests apiRequestsObject) {
        apiRequestsObject.fetchOrderList(new OrderListFetchedCallback(mView));
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
                if(view != null){
                    view.onOrderListFetched(orderList);
                }
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
                String productImagePath = "http://admin.airporterinc.com" + orderArray.getJSONObject(x).getString("imagePath");
                String orderDateTime = orderArray.getJSONObject(x).getString("orderDateTime");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(orderDateTime);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

                //lastOrderIdDisplayed = orderId;
                Order order = new Order(orderId, shopperName, productName, deliverFrom, deliverTo, deliverBefore, price, productImagePath , orderDateTime);
                orderList.add(order);
            }
        }

        @Override
        public void onFail(VolleyError error) {
            Toast.makeText(AppController.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.airporter.MenuModule.OffersFragment.OffersReceivedFragment;

import com.android.volley.VolleyError;
import com.example.airporter.data.OffersReceived;
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

public class OffersReceivedPresenter implements OffersReceivedMvpContract {
    private OffersReceivedView view;
    private ApiRequestManager apiRequestObject;

    public OffersReceivedPresenter(OffersReceivedView view, ApiRequestManager apiRequestObject) {
        this.view = view;
        this.apiRequestObject = apiRequestObject;
    }

    @Override
    public void fetchOffersReceived() {
        apiRequestObject.fetchOffersReceived(new OnOffersReceivedCallback(view), CONSTANTS.NetworkRequestTags.FETCH_OFFERS_RECEIVED);
    }

    private static class OnOffersReceivedCallback implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<OffersReceivedView> mView;
        private ArrayList<OffersReceived> offersReceivedList = new ArrayList<>();

        public OnOffersReceivedCallback(OffersReceivedView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            OffersReceivedView view = mView.get();
            if (view != null) {
                try {
                    parseJsonResponse(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                view.onOffersReceivedFetched(offersReceivedList);
            }
        }

        @Override
        public void onFail(VolleyError error) {
            OffersReceivedView view = mView.get();
            if (view != null) {

            }
        }

        private void parseJsonResponse(JSONObject response) throws ParseException, JSONException {
            JSONArray orderArray = response.getJSONArray("data");
            for (int x = 0; x < orderArray.length(); x++) {
                String orderId = orderArray.getJSONObject(x).getString("orderId");
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
                OffersReceived offer = new OffersReceived(orderId,productName,deliverFrom,deliverTo,deliverBefore,price,productImagePath,orderDateTime);
                offersReceivedList.add(offer);
            }
        }
    }
}

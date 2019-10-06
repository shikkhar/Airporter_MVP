package com.example.airporter.Menu.OffersFragment.OffersSentFragment;

import com.android.volley.VolleyError;
import com.example.airporter.data.OffersSent;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.FETCH_OFFERS_SENT;
import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.UPDATE_OFFER;
import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.WITHDRAW_OFFER;

public class OffersSentPresenter implements OffersSentMvpContract {
    private OffersSentView mView;
    private ApiRequestManager apiRequestObject;

    public OffersSentPresenter(OffersSentView mView, ApiRequestManager apiRequestObject) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
    }

    @Override
    public void fetchOffersSent() {
        apiRequestObject.fetchOffersSent(new FetchOffersSentCallback(mView), FETCH_OFFERS_SENT);
    }

    @Override
    public void withdrawOffer(String offerId) {
        apiRequestObject.withdrawOffer(offerId, new WithdrawOfferCallback(mView), WITHDRAW_OFFER);
    }

    @Override
    public void updateOffer(String offerId, String offerPrice) {
        apiRequestObject.updateOffer(offerId, offerPrice,new UpdateOfferCallback(mView) ,UPDATE_OFFER);
    }

    private static class FetchOffersSentCallback implements VolleySeverRequest.VolleyResponseCallback {

        private WeakReference<OffersSentView> mView;
        private ArrayList<OffersSent> offersSentList;

        public FetchOffersSentCallback(OffersSentView view) {
            this.mView = new WeakReference<>(view);
            offersSentList = new ArrayList<>();
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OffersSentView view = mView.get();
            if(view!=null){
                view.onOffersSentFetched(offersSentList);
            }
        }

        @Override
        public void onFail(VolleyError error) {
            OffersSentView view = mView.get();
            if(view!=null){
            }
        }

        private void parseJsonResponse(JSONObject response) throws JSONException {
            JSONArray orderArray = response.getJSONArray("data");
            for (int x = 0; x < orderArray.length(); x++) {
                String orderId = orderArray.getJSONObject(x).getString("orderId");
                String offerId = orderArray.getJSONObject(x).getString("offerId");
                String shopperName = orderArray.getJSONObject(x).getString("firstName") + " " + orderArray.getJSONObject(x).getString("lastName");
                String offerAccepted = orderArray.getJSONObject(x).getString("offerAccepted");
                String offerRejected = orderArray.getJSONObject(x).getString("offerRejected");
                String offerCancelled = orderArray.getJSONObject(x).getString("offerCancelled");
                String productName = orderArray.getJSONObject(x).getString("productTitle");
                String deliverFrom = orderArray.getJSONObject(x).getString("deliverFrom");
                String deliverTo = orderArray.getJSONObject(x).getString("deliverTo");
                String deliverBefore = orderArray.getJSONObject(x).getString("deliverBefore");
                String price = orderArray.getJSONObject(x).getString("price");
                String offerPrice = orderArray.getJSONObject(x).getString("offerPrice");
                String orderImage = "http://admin.airporterinc.com" + orderArray.getJSONObject(x).getString("imagePath");
                String orderDateTime = orderArray.getJSONObject(x).getString("orderDateTime");
                String orderInactive = orderArray.getJSONObject(x).getString("orderPicked");
                String shopperImagePath = "http://airporterinc.com" +  orderArray.getJSONObject(x).getString("shopperImagePath");
                double reward = Double.valueOf(offerPrice) - Double.valueOf(price);


                OffersSent offerSent = new OffersSent(orderId, offerId, shopperName, productName, deliverFrom, deliverTo, deliverBefore,
                        price, orderImage, orderDateTime,offerPrice, offerAccepted, offerRejected, offerCancelled, orderInactive , String.valueOf(reward), shopperImagePath);
                offersSentList.add(offerSent);
            }
        }
    }

    private static class WithdrawOfferCallback implements VolleySeverRequest.VolleyResponseCallback{

        private WeakReference<OffersSentView> mView;

        public WithdrawOfferCallback(OffersSentView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                String result = response.getString("success");
                if (result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                    OffersSentView view = mView.get();
                    if(view!=null){
                        view.onOfferWithdrawn();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            OffersSentView view = mView.get();
            if(view!=null){
            }
        }
    }

    private static class UpdateOfferCallback implements VolleySeverRequest.VolleyResponseCallback{

        private WeakReference<OffersSentView> mView;

        public UpdateOfferCallback(OffersSentView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                String result = response.getString("success");
                if (result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                    OffersSentView view = mView.get();
                    if(view!=null){
                        view.onOfferUpdated();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            OffersSentView view = mView.get();
            if(view!=null){
            }
        }
    }
}

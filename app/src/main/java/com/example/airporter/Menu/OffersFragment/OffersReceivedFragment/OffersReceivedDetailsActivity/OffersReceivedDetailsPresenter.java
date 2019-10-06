package com.example.airporter.Menu.OffersFragment.OffersReceivedFragment.OffersReceivedDetailsActivity;

import com.android.volley.VolleyError;
import com.example.airporter.data.OffersReceivedDetails;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.ArrayList;

public class OffersReceivedDetailsPresenter implements OffersReceivedDetailsMvpContract {
    private OffersReceivedDetailsView mView;
    private ApiRequestManager apiRequestObject;
    //TODO: these fields are used so that we can update porter id once offer has been accepted...integrate that in one query and remove these fields
    String bidderId;
    String orderId;
    String offerId;

    public OffersReceivedDetailsPresenter(OffersReceivedDetailsView mView, ApiRequestManager apiRequestObject) {
        this.mView = mView;
        this.apiRequestObject = apiRequestObject;
    }

    @Override
    public void fetchOfferDetails(String orderId) {
        apiRequestObject.fetchOfferDetails(orderId, new FetchOfferDetailsCallback(mView), CONSTANTS.NetworkRequestTags.FETCH_OFFER_DETAILS);
    }

    @Override
    public void acceptOffer(String orderId, String offerId, String bidderId) {
        this.bidderId = bidderId;
        this.orderId = orderId;
        this.offerId = offerId;
        apiRequestObject.acceptOffer(orderId, offerId, new OfferAcceptedCallback(orderId, offerId, bidderId, mView), CONSTANTS.NetworkRequestTags.ACCEPT_OFFER);
    }

    @Override
    public void rejectOffer(String orderId, String offerId) {
        apiRequestObject.rejectOffer(orderId, offerId, new OfferRejectedCallback(mView), CONSTANTS.NetworkRequestTags.REJECT_OFFER);
    }


    private static class FetchOfferDetailsCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<OffersReceivedDetailsView> mView;
        private ArrayList<OffersReceivedDetails> offerDetailsList = new ArrayList<>();

        public FetchOfferDetailsCallback(OffersReceivedDetailsView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                parseJsonResponse(response);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OffersReceivedDetailsView view = mView.get();
            if(view!=null)
                view.onOfferDetailsFetched(offerDetailsList);
        }


        @Override
        public void onFail(VolleyError error) {
            OffersReceivedDetailsView view = mView.get();
            if(view!=null){

            }
        }

        private void parseJsonResponse(JSONObject response) throws ParseException, JSONException {
            JSONArray offerArray = response.getJSONArray("data");

            for (int x = 0; x < offerArray.length(); x++) {
//                String orderId = offerArray.getJSONObject(x).getString("orderId");
                String offerId = offerArray.getJSONObject(x).getString("offerId");
                String bidderId = offerArray.getJSONObject(x).getString("bidderId");
                String bidderName = offerArray.getJSONObject(x).getString("bidderFirstName") + " " + offerArray.getJSONObject(x).getString("bidderLastName");
                String price = offerArray.getJSONObject(x).getString("price");
                String bidderImage = "https://airporterinc.com"+offerArray.getJSONObject(x).getString("userImagePath");
                String offerPrice = offerArray.getJSONObject(x).getString("offerPrice");

                double reward = Double.valueOf(offerPrice) - Double.valueOf(price);

                OffersReceivedDetails offer= new OffersReceivedDetails(null,offerId,bidderId,bidderName,price,bidderImage,offerPrice, String.valueOf(reward));
                offerDetailsList.add(offer);

            }
        }
    }

    private static class OfferAcceptedCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<OffersReceivedDetailsView> mView;
        private String orderId;
        private String offerId;
        private String bidderId;

        public OfferAcceptedCallback(String orderId,String offerId, String bidderId, OffersReceivedDetailsView view) {
            this.orderId = orderId;
            this.offerId = offerId;
            this.bidderId = bidderId;
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            String result = null;
            try {
                result = response.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                ApiRequestManager.getInstance().rejectOtherOffers(orderId, offerId, new RejectOtherOffersCallback(orderId, bidderId, mView), CONSTANTS.NetworkRequestTags.REJECT_OTHER__OFFERS);
            }
        }

        @Override
        public void onFail(VolleyError error) {

        }
    }


    private static class OfferRejectedCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<OffersReceivedDetailsView> mView;

        public OfferRejectedCallback(OffersReceivedDetailsView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            String result = null;
            try {
                result = response.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                OffersReceivedDetailsView view = mView.get();
                if(view!=null){
                    view.onOfferRejected();
                }
            }
        }

        @Override
        public void onFail(VolleyError error) {

        }
    }

    private static class RejectOtherOffersCallback implements VolleySeverRequest.VolleyResponseCallback{
        WeakReference<OffersReceivedDetailsView> mView;
        String orderId;
        String bidderId;


        public RejectOtherOffersCallback(String orderId, String bidderId, WeakReference<OffersReceivedDetailsView> view) {
            this.orderId = orderId;
            this.bidderId = bidderId;
            this.mView = view;
        }

        @Override
        public void onSuccess(JSONObject response) {
            String result = null;
            try {
                result = response.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                ApiRequestManager.getInstance().updatePorterId(orderId, bidderId, new UpdatePorterCallback(mView), CONSTANTS.NetworkRequestTags.UPDATE_PORTER);
            }
        }

        @Override
        public void onFail(VolleyError error) {

        }
    }

    private static class UpdatePorterCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<OffersReceivedDetailsView> mView;

        public UpdatePorterCallback(WeakReference<OffersReceivedDetailsView> view) {
            this.mView = view;
        }

        @Override
        public void onSuccess(JSONObject response) {
            String result = null;
            try {
                result = response.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)){
                OffersReceivedDetailsView view = mView.get();
                if(view!=null){
                    view.onOfferAccepted();
                }
            }
        }

        @Override
        public void onFail(VolleyError error) {

        }
    }
}

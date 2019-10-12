package com.example.airporter.MenuModule.OffersFragmentModule.OffersReceivedFragmentModule.OffersReceivedDetailsActivity;

import com.example.airporter.data.OffersReceivedDetails;

import java.util.ArrayList;

public interface OffersReceivedDetailsMvpContract {

    void fetchOfferDetails(String orderId);
    void acceptOffer(String orderId, String offerId, String bidderId);
    void rejectOffer(String orderId, String offerId);
    //void messageBidder();


    interface OffersReceivedDetailsView{
        void onOfferDetailsFetched(ArrayList<OffersReceivedDetails> offerDetailsList);
        void onOfferAccepted();
        void onOfferRejected();
    }
}

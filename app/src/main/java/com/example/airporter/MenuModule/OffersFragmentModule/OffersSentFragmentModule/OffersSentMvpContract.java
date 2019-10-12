package com.example.airporter.MenuModule.OffersFragmentModule.OffersSentFragmentModule;

import com.example.airporter.data.OffersSent;

import java.util.ArrayList;

public interface OffersSentMvpContract {

    void fetchOffersSent();
    void withdrawOffer(String offerId);
    void updateOffer(String offerId, String offerPrice);

    interface OffersSentView{
        void onOffersSentFetched(ArrayList<OffersSent> offersSentList);
        void onOfferWithdrawn();
        void onOfferUpdated();
    }
}

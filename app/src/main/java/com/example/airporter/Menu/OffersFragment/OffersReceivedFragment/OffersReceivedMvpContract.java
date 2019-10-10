package com.example.airporter.Menu.OffersFragment.OffersReceivedFragment;

import com.example.airporter.data.OffersReceived;

import java.util.ArrayList;

public interface OffersReceivedMvpContract {

    void fetchOffersReceived();

    interface OffersReceivedView{
        void onOffersReceivedFetched(ArrayList<OffersReceived> offersReceivedList);
    }
}

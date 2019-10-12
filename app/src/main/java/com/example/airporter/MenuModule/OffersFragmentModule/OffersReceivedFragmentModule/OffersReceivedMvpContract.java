package com.example.airporter.MenuModule.OffersFragmentModule.OffersReceivedFragmentModule;

import com.example.airporter.data.OffersReceived;

import java.util.ArrayList;

public interface OffersReceivedMvpContract {

    void fetchOffersReceived();

    interface OffersReceivedView{
        void onOffersReceivedFetched(ArrayList<OffersReceived> offersReceivedList);
    }
}

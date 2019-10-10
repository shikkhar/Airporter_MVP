package com.example.airporter.MenuModule.OrdersFragment.InTransitOrderFragment;

import com.example.airporter.data.Order;

import java.util.ArrayList;

public interface InTransitOrderMvpContract {

    void fetchInTransitOrders();

    interface InTransitOrderView{

        void onTransitOrdersFetched(ArrayList<Order> inTransitOrderList);
    }
}

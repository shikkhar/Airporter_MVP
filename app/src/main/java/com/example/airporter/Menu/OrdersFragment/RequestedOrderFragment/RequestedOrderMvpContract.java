package com.example.airporter.Menu.OrdersFragment.RequestedOrderFragment;

import com.example.airporter.data.Order;

import java.util.ArrayList;

public interface RequestedOrderMvpContract {

    void fetchRequestedOrders();

    interface RequestedOrderView{

        void onRequestedOrdersFetched(ArrayList<Order> requestedOrderList);
    }
}

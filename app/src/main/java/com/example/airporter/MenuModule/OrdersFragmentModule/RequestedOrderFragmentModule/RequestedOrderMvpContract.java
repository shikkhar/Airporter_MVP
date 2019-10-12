package com.example.airporter.MenuModule.OrdersFragmentModule.RequestedOrderFragmentModule;

import com.example.airporter.data.Order;

import java.util.ArrayList;

public interface RequestedOrderMvpContract {

    void fetchRequestedOrders();

    interface RequestedOrderView{

        void onRequestedOrdersFetched(ArrayList<Order> requestedOrderList);
    }
}

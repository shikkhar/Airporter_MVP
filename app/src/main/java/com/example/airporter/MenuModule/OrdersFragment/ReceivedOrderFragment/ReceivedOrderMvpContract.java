package com.example.airporter.MenuModule.OrdersFragment.ReceivedOrderFragment;

import com.example.airporter.data.Order;

import java.util.ArrayList;

public interface ReceivedOrderMvpContract {

    void fetchReceivedOrders();

    interface ReceivedOrderView{

        void onReceivedOrdersFetched(ArrayList<Order> receivedOrderList);
    }
}

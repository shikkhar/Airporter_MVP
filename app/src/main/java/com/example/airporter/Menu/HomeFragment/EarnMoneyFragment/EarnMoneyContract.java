package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;

import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequests;

import java.util.List;

public interface EarnMoneyContract {

    void fetchOrderList(ApiRequests apiRequestsObject);
    interface EarnMoneyView {
        void onOrderListFetched(List<Order> orderList);
    }
}

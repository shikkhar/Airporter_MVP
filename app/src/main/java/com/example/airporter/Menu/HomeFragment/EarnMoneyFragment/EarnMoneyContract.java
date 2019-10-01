package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;

import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;

import java.util.List;

public interface EarnMoneyContract {

    void fetchOrderList();
    void fetchMoreOrders(String lastOrderIdFetched);
    interface EarnMoneyView {
        void onOrderListFetched(List<Order> orderList);
    }
}

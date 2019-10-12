package com.example.airporter.MenuModule.OrdersFragmentModule;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.MenuModule.OrdersFragmentModule.DeliveredOrderFragmentModule.DeliveredOrderFragment;
import com.example.airporter.MenuModule.OrdersFragmentModule.InTransitOrderFragmentModule.InTransitOrderFragment;
import com.example.airporter.MenuModule.OrdersFragmentModule.ReceivedOrderFragmentModule.ReceivedOrderFragment;
import com.example.airporter.MenuModule.OrdersFragmentModule.RequestedOrderFragmentModule.RequestedOrderFragment;
import com.example.airporter.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private OrderFragmentPagerAdapter mAdapter;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitleList;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentList = new ArrayList<>();
        tabTitleList = new ArrayList<>();
        setupFragmentList();
        mAdapter = new OrderFragmentPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragmentList, tabTitleList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_orders, container, false);

        tabLayout = view.findViewById(R.id.tabLayoutOrders);
        viewPager = view.findViewById(R.id.viewPagerOrderId);

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupFragmentList() {
        fragmentList.add(new RequestedOrderFragment());
        fragmentList.add(new InTransitOrderFragment());
        fragmentList.add(new ReceivedOrderFragment());
        fragmentList.add(new DeliveredOrderFragment());

        tabTitleList.add(getResources().getString(R.string.tab_item_requested));
        tabTitleList.add(getResources().getString(R.string.tab_item_in_transit));
        tabTitleList.add(getResources().getString(R.string.tab_item_received));
        tabTitleList.add(getResources().getString(R.string.tab_item_delivered));
    }

}

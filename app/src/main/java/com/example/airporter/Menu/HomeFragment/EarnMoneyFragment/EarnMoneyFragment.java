package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airporter.R;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequests;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarnMoneyFragment extends Fragment implements EarnMoneyContract.EarnMoneyView {
    private RecyclerView recyclerView;
    private Button submitOfferButton;
    private List<Order> orderList;
    private EarnMoneyRecyclerViewAdapter mAdapter;
    private EarnMoneyPresenter mPresenter;

    public EarnMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderList = new ArrayList<>();
        mPresenter = new EarnMoneyPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_earn_money, container, false);

        submitOfferButton = view.findViewById(R.id.submitOfferButtonId);
        recyclerView = view.findViewById(R.id.earnMoneyRecyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mPresenter.fetchOrderList(ApiRequests.getInstance());

        return view;
    }

    @Override
    public void onOrderListFetched(List<Order> orderList) {
        this.orderList = orderList;
        mAdapter = new EarnMoneyRecyclerViewAdapter(orderList, this.getContext());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}

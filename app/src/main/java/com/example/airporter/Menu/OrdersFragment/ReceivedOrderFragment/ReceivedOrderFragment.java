package com.example.airporter.Menu.OrdersFragment.ReceivedOrderFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.Menu.OrdersFragment.RequestedOrderFragment.RequestedOrderPresenter;
import com.example.airporter.Menu.OrdersFragment.RequestedOrderFragment.RequestedOrdersRecyclerViewAdapter;
import com.example.airporter.R;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedOrderFragment extends Fragment implements ReceivedOrderMvpContract.ReceivedOrderView {
    private RecyclerView recyclerView;
    private ReceivedOrdersRecyclerViewAdapter mAdapter;
    private ArrayList<Order> receivedOrderList;
    private ReceivedOrderPresenter mPresenter;

    public ReceivedOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReceivedOrderPresenter(this, ApiRequestManager.getInstance());
        receivedOrderList = new ArrayList<>();
        mAdapter = new ReceivedOrdersRecyclerViewAdapter(receivedOrderList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_received_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewReceivedOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        receivedOrderList.clear();
        mPresenter.fetchReceivedOrders();
    }

    @Override
    public void onReceivedOrdersFetched(ArrayList<Order> receivedOrderList) {
        this.receivedOrderList.addAll(receivedOrderList);
        mAdapter.notifyDataSetChanged();
    }
}

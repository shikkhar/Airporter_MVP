package com.example.airporter.MenuModule.OrdersFragment.RequestedOrderFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.R;
import com.example.airporter.data.Order;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestedOrderFragment extends Fragment implements RequestedOrderMvpContract.RequestedOrderView {
private RecyclerView recyclerView;
private RequestedOrdersRecyclerViewAdapter mAdapter;
private ArrayList<Order> requestedOrderList;
private RequestedOrderPresenter mPresenter;

    public RequestedOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RequestedOrderPresenter(this, ApiRequestManager.getInstance());
        requestedOrderList = new ArrayList<>();
        mAdapter = new RequestedOrdersRecyclerViewAdapter(requestedOrderList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requested_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRequestedOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestedOrderList.clear();
        mPresenter.fetchRequestedOrders();
    }

    @Override
    public void onRequestedOrdersFetched(ArrayList<Order> requestedOrderList) {
        this.requestedOrderList.addAll(requestedOrderList);
        mAdapter.notifyDataSetChanged();
    }
}

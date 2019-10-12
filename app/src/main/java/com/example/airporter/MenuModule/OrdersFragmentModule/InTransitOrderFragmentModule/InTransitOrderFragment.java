package com.example.airporter.MenuModule.OrdersFragmentModule.InTransitOrderFragmentModule;


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
public class InTransitOrderFragment extends Fragment implements InTransitOrderMvpContract.InTransitOrderView {
    private RecyclerView recyclerView;
    private InTransitOrdersRecyclerViewAdapter mAdapter;
    private ArrayList<Order> inTransitOrderList;
    private InTransitOrderPresenter mPresenter;

    public InTransitOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new InTransitOrderPresenter(this, ApiRequestManager.getInstance());
        inTransitOrderList = new ArrayList<>();
        mAdapter = new InTransitOrdersRecyclerViewAdapter(inTransitOrderList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_in_transit_order, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewInTransitOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        inTransitOrderList.clear();
        mPresenter.fetchInTransitOrders();
    }

    @Override
    public void onTransitOrdersFetched(ArrayList<Order> inTransitOrderList) {
        this.inTransitOrderList.addAll(inTransitOrderList);
        mAdapter.notifyDataSetChanged();
    }
}

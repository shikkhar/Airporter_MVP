package com.example.airporter.MenuModule.OffersFragmentModule.OffersReceivedFragmentModule;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.MenuModule.OffersFragmentModule.OffersReceivedFragmentModule.OffersReceivedDetailsActivity.OffersReceivedDetailsActivity;
import com.example.airporter.R;
import com.example.airporter.data.OffersReceived;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersReceivedFragment extends Fragment implements OffersReceivedMvpContract.OffersReceivedView {
    private static final String OFFERS_RECEIVED_DETAILS_TAG = "0";
    public static final int REQUEST_CODE_OFFER_DETAILS_ACTIVITY = 0;

    private RecyclerView recyclerView;
    private OffersReceivedPresenter mPresenter;
    private OffersReceivedRecyclerViewAdapter mAdapter;
    private ArrayList<OffersReceived> offersReceivedList;

    public OffersReceivedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersReceivedList = new ArrayList<>();
        mAdapter = new OffersReceivedRecyclerViewAdapter(offersReceivedList, this);
        mPresenter = new OffersReceivedPresenter(this, ApiRequestManager.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers_received, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewOffersReceivedId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        offersReceivedList.clear();
        mPresenter.fetchOffersReceived();
    }

    @Override
    public void onOffersReceivedFetched(ArrayList<OffersReceived> offersReceivedList) {
        this.offersReceivedList.addAll(offersReceivedList);
        mAdapter.notifyDataSetChanged();
    }

    public void onRecyclerViewClicked(String orderId){
        Intent intent = new Intent(getContext(), OffersReceivedDetailsActivity.class);
        intent.putExtra("orderId", orderId);
        startActivityForResult(intent, REQUEST_CODE_OFFER_DETAILS_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_OFFER_DETAILS_ACTIVITY && resultCode == RESULT_OK){
            offersReceivedList.clear();
            //mPresenter.fetchOffersReceived();
        }
    }
}

package com.example.airporter.MenuModule.OffersFragmentModule.OffersReceivedFragmentModule.OffersReceivedDetailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.airporter.R;
import com.example.airporter.data.OffersReceivedDetails;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

public class OffersReceivedDetailsActivity extends AppCompatActivity implements OffersReceivedDetailsMvpContract.OffersReceivedDetailsView {
    private OffersReceivedDetailsPresenter mPresenter;
    private RecyclerView recyclerView;
    private OffersReceivedDetailsAdapter mAdapter;
    private ArrayList<OffersReceivedDetails> offerDetailsList;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_received_details);
        mPresenter = new OffersReceivedDetailsPresenter(this, ApiRequestManager.getInstance());
        offerDetailsList = new ArrayList<>();
        mAdapter = new OffersReceivedDetailsAdapter(offerDetailsList, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("orderId"))
            orderId = bundle.getString("orderId");
        recyclerView = this.findViewById(R.id.recyclerViewOffersReceivedDetailsId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.fetchOfferDetails(orderId);
    }

    @Override
    public void onOfferDetailsFetched(ArrayList<OffersReceivedDetails> offerDetailsList) {
        this.offerDetailsList.addAll(offerDetailsList);
        mAdapter.notifyDataSetChanged();
    }

    public void onAcceptOfferClick(String offerId, String bidderId){
        mPresenter.acceptOffer(orderId, offerId, bidderId);
    }

    public void onRejectOfferClick(String offerId){
        mPresenter.rejectOffer(orderId, offerId);
    }

    public void onMessageBidderClick(){
        //TODO: implement
    }

    @Override
    public void onOfferAccepted() {
        Toast.makeText(getApplicationContext(), "Offer accepted successfully", Toast.LENGTH_SHORT).show();
        offerDetailsList.clear();
        mAdapter.notifyDataSetChanged();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onOfferRejected() {
        Toast.makeText(getApplicationContext(), "Offer rejected", Toast.LENGTH_SHORT).show();
        offerDetailsList.clear();
        mPresenter.fetchOfferDetails(orderId);
    }
}

package com.example.airporter.Menu.OffersFragment.OffersSentFragment;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.airporter.R;
import com.example.airporter.data.OffersSent;
import com.example.airporter.helper.ApiRequestManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersSentFragment extends Fragment implements OffersSentMvpContract.OffersSentView, ModifyOfferDialogFragment.ModifyOfferCallback {
    private static final String MODIFY_OFFER_DIALOG_TAG = "0";
    private RecyclerView recyclerView;
    private OffersSentRecyclerViewAdapter mAdapter;
    private ArrayList<OffersSent> offersSentList;
    private OffersSentPresenter mPresenter;
    private ModifyOfferDialogFragment modifyOfferDialogFragment;
    private Dialog mOverlayDialog;

    public OffersSentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersSentList = new ArrayList<>();
        mPresenter = new OffersSentPresenter(this, ApiRequestManager.getInstance());
        mAdapter = new OffersSentRecyclerViewAdapter(offersSentList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_offers_sent, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewOffersSentId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        offersSentList.clear();
        mPresenter.fetchOffersSent();
    }

    @Override
    public void onOffersSentFetched(ArrayList<OffersSent> offersSentList) {
        this.offersSentList.addAll(offersSentList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOfferWithdrawn() {
        Toast.makeText(getContext().getApplicationContext(), "Offer Withdrawn", Toast.LENGTH_LONG);
        offersSentList.clear();
        mPresenter.fetchOffersSent();
    }

    @Override
    public void onOfferUpdated() {
        Toast.makeText(getContext().getApplicationContext(), "Offer Updated", Toast.LENGTH_LONG);
        mOverlayDialog.dismiss();
        offersSentList.clear();
        mPresenter.fetchOffersSent();
    }

    public void onWithdrawOfferClicked(String offerId) {
        mPresenter.withdrawOffer(offerId);
    }

    public void onModifyOfferClicked(OffersSent offerSent) {

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag(MODIFY_OFFER_DIALOG_TAG);
        if(prev!=null)
            ft.remove(prev);
        ft.addToBackStack(null);

        modifyOfferDialogFragment = new ModifyOfferDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("offer", offerSent);
        modifyOfferDialogFragment.setArguments(args);
        modifyOfferDialogFragment.show(ft, MODIFY_OFFER_DIALOG_TAG);
    }

    @Override
    public void updateOffer(String offerId, String offerPrice) {
        modifyOfferDialogFragment.dismiss();

        if (mOverlayDialog == null)
            mOverlayDialog = new Dialog(getContext(), android.R.style.Theme_Panel);
        mOverlayDialog.setCancelable(false);
        mOverlayDialog.show();
        mPresenter.updateOffer(offerId, offerPrice);
    }
}

package com.example.airporter.MenuModule.OffersFragment.OffersReceivedFragment.OffersReceivedDetailsActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airporter.R;
import com.example.airporter.data.OffersReceivedDetails;

import java.util.ArrayList;

public class OffersReceivedDetailsAdapter extends RecyclerView.Adapter<OffersReceivedDetailsAdapter.MyViewHolder> {
    private ArrayList<OffersReceivedDetails> offerDetailsList;
    private OffersReceivedDetailsActivity offersReceivedDetailsActivity;

    public OffersReceivedDetailsAdapter(ArrayList<OffersReceivedDetails> offerDetailsList, OffersReceivedDetailsActivity activity) {
        this.offerDetailsList = offerDetailsList;
        this.offersReceivedDetailsActivity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offers_received_details, parent, false);
        return new MyViewHolder(view,
                new OnAcceptOfferClickListener(),
                new OnRejectOfferClickListener(),
                new OnMessageClickListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OffersReceivedDetails offerDetails = offerDetailsList.get(position);
        holder.onAcceptOfferClickListener.setPosition(position);
        holder.onRejectOfferClickListener.setPosition(position);
        holder.onMessageClickListener.setPosition(position);

        holder.bidderNameTextView.setText(offerDetails.getBidderName());
        holder.productPriceTextView.setText(offerDetails.getPrice());
        holder.offerPriceTextView.setText(offerDetails.getOfferPrice());
        holder.rewardTextView.setText(offerDetails.getReward());
    }

    @Override
    public int getItemCount() {
        return offerDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView bidderDisplayImage;
        private TextView bidderNameTextView;
        private TextView productPriceTextView;
        private TextView offerPriceTextView;
        private TextView rewardTextView;
        private ImageButton acceptOfferButton;
        private ImageButton rejectOfferButton;
        private ImageButton messageBidderButton;
        private OnAcceptOfferClickListener onAcceptOfferClickListener;
        private OnRejectOfferClickListener onRejectOfferClickListener;
        private OnMessageClickListener onMessageClickListener;

        public MyViewHolder(@NonNull View itemView,
                            OnAcceptOfferClickListener onAcceptOfferClickListener,
                            OnRejectOfferClickListener onRejectOfferClickListener,
                            OnMessageClickListener onMessageClickListener) {
            super(itemView);
            this.bidderDisplayImage = itemView.findViewById(R.id.imageViewBidderId);
            this.bidderNameTextView = itemView.findViewById(R.id.textViewBidderName);
            this.productPriceTextView = itemView.findViewById(R.id.textViewProductpriceId);
            this.offerPriceTextView = itemView.findViewById(R.id.textViewOfferPriceId);
            this.rewardTextView = itemView.findViewById(R.id.textViewRewardId);
            this.acceptOfferButton = itemView.findViewById(R.id.imageButtonAcceptOfferId);
            this.rejectOfferButton = itemView.findViewById(R.id.imageButtonRejectOfferId);
            this.messageBidderButton = itemView.findViewById(R.id.imageButtonMessageBidderId);
            this.onAcceptOfferClickListener = onAcceptOfferClickListener;
            acceptOfferButton.setOnClickListener(onAcceptOfferClickListener);
            this.onRejectOfferClickListener = onRejectOfferClickListener;
            rejectOfferButton.setOnClickListener(onRejectOfferClickListener);
            this.onMessageClickListener = onMessageClickListener;
            messageBidderButton.setOnClickListener(onMessageClickListener);

        }
    }

    private class OnAcceptOfferClickListener implements View.OnClickListener{
        int position;

        @Override
        public void onClick(View v) {
            String offerId = offerDetailsList.get(position).getOfferId();
            String bidderId = offerDetailsList.get(position).getBidderId();
            offersReceivedDetailsActivity.onAcceptOfferClick(offerId, bidderId);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private class OnRejectOfferClickListener implements View.OnClickListener{
        int position;

        @Override
        public void onClick(View v) {
            String offerId = offerDetailsList.get(position).getOfferId();
            offersReceivedDetailsActivity.onRejectOfferClick(offerId);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private class OnMessageClickListener implements View.OnClickListener{
        int position;
        @Override
        public void onClick(View v) {

        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

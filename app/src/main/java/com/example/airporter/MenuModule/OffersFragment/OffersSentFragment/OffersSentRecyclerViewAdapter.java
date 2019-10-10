package com.example.airporter.MenuModule.OffersFragment.OffersSentFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.airporter.R;
import com.example.airporter.data.OffersSent;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OffersSentRecyclerViewAdapter extends RecyclerView.Adapter<OffersSentRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<OffersSent> offersSentList;
    private OffersSentFragment containerFragment;
    private Context appContext;

    public OffersSentRecyclerViewAdapter(ArrayList<OffersSent> offersSentList, OffersSentFragment containerFragment) {
        this.offersSentList = offersSentList;
        this.containerFragment = containerFragment;
        this.appContext = containerFragment.getContext().getApplicationContext();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offers_sent, parent, false);
        return new MyViewHolder(view, new OnWithdrawOfferClickListener(), new OnModifyOfferClickListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OffersSent offerSent = offersSentList.get(position);
        holder.onWithdrawOfferClickListener.setPosition(position);
        holder.onModifyOfferClickListener.setPosition(position);
        holder.updateOfferGroup.setVisibility(View.VISIBLE);
        holder.modifyOfferButton.setEnabled(true);
        holder.modifyOfferButton.setAlpha((float) 1);
        holder.withdrawOfferButton.setEnabled(true);
        holder.withdrawOfferButton.setAlpha((float) 1);

        Glide.with(containerFragment.getContext().getApplicationContext()).load(offerSent.getShopperImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.shopperImage);

        Glide.with(containerFragment.getContext().getApplicationContext()).load(offerSent.getOrderImage())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.productImage);

        holder.shopperNameTextView.setText(offerSent.getShopperName());
        holder.productNameTextView.setText(offerSent.getProductName());
        holder.deliverToTextView.setText(offerSent.getDeliverTo());
        holder.deliverFromTextView.setText(offerSent.getDeliverFrom());
        holder.deliverBeforeTextView.setText(offerSent.getDeliverBefore());
        holder.productPriceTextView.setText(offerSent.getPrice());
        holder.offerPriceTextView.setText(offerSent.getOfferPrice());
        holder.rewardTextView.setText(offerSent.getReward());
        formatView(offerSent, holder);

    }

    private void formatView(OffersSent offerSent, MyViewHolder holder) {

        if (offerSent.getOrderInactive().equals("1")) {
            if (offerSent.getOfferAccepted().equals("1")) {
                holder.statusTextView.setText("ACCEPTED");
                holder.statusTextView.setTextColor(ContextCompat.getColor(appContext, R.color.textGreen));

            } else {
                holder.statusTextView.setText("CLOSED");
                holder.statusTextView.setTextColor(ContextCompat.getColor(appContext, R.color.textRed));
            }

            holder.updateOfferGroup.setVisibility(View.GONE);

        } else if (offerSent.getOfferRejected().equals("1")) {
            holder.statusTextView.setText("REJECTED");
            holder.statusTextView.setTextColor(ContextCompat.getColor(appContext, R.color.textRed));
            //offerSentDataCard.setCardBackgroundColor(context.getResources().getColor(R.color.cardBackgroundRed));
            holder.withdrawOfferButton.setEnabled(false);
            holder.withdrawOfferButton.setAlpha((float) 0.25);

        } else if (offerSent.getOfferCancelled().equals("1")) {
            holder.statusTextView.setText("WITHDRAWN");
            holder.statusTextView.setTextColor(ContextCompat.getColor(appContext, R.color.textOrange));
            holder.withdrawOfferButton.setEnabled(false);
            holder.withdrawOfferButton.setAlpha((float) 0.25);
        } else {
            holder.withdrawOfferButton.setEnabled(true);
            holder.statusTextView.setTextColor(ContextCompat.getColor(appContext, R.color.textYellow));
            holder.statusTextView.setText("NO RESPONSE");
        }
    }

    @Override
    public int getItemCount() {
        return offersSentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private OnWithdrawOfferClickListener onWithdrawOfferClickListener;
        private OnModifyOfferClickListener onModifyOfferClickListener;
        private ImageView shopperImage;
        private ImageView productImage;
        private TextView shopperNameTextView;
        private TextView statusTextView;
        private TextView productNameTextView;
        private TextView deliverToTextView;
        private TextView deliverFromTextView;
        private TextView deliverBeforeTextView;
        private TextView productPriceTextView;
        private TextView offerPriceTextView;
        private TextView rewardTextView;
        private MaterialButton withdrawOfferButton;
        private MaterialButton modifyOfferButton;
        private Group updateOfferGroup;

        public MyViewHolder(@NonNull View itemView,
                            OnWithdrawOfferClickListener onWithdrawOfferClickListener,
                            OnModifyOfferClickListener onModifyOfferClickListener) {
            super(itemView);
            this.onModifyOfferClickListener = onModifyOfferClickListener;
            this.onWithdrawOfferClickListener = onWithdrawOfferClickListener;
            this.shopperImage = itemView.findViewById(R.id.displayImageViewId);
            this.productImage = itemView.findViewById(R.id.productImageViewId);
            this.shopperNameTextView = itemView.findViewById(R.id.shopperNameTextViewId);
            this.statusTextView = itemView.findViewById(R.id.textViewOrderStatusId);
            this.productNameTextView = itemView.findViewById(R.id.productTitleTextViewId);
            this.deliverToTextView = itemView.findViewById(R.id.deliverToTextViewid);
            this.deliverFromTextView = itemView.findViewById(R.id.deliverFromTextViewId);
            this.deliverBeforeTextView = itemView.findViewById(R.id.deliverBeforeTextViewId);
            this.productPriceTextView = itemView.findViewById(R.id.productPriceTextViewId);
            this.offerPriceTextView = itemView.findViewById(R.id.textViewOfferPriceId);
            this.rewardTextView = itemView.findViewById(R.id.textViewRewardId);
            this.withdrawOfferButton = itemView.findViewById(R.id.buttonWithdrawOfferId);
            this.modifyOfferButton = itemView.findViewById(R.id.buttonModifyOfferId);
            this.updateOfferGroup = itemView.findViewById(R.id.viewGroupUpdateOfferId);
            this.modifyOfferButton.setOnClickListener(onModifyOfferClickListener);
            this.withdrawOfferButton.setOnClickListener(onWithdrawOfferClickListener);
        }
    }

    private class OnWithdrawOfferClickListener implements View.OnClickListener {
        int position;

        @Override
        public void onClick(View v) {
            containerFragment.onWithdrawOfferClicked(offersSentList.get(position).getOfferId());
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private class OnModifyOfferClickListener implements View.OnClickListener {
        int position;

        @Override
        public void onClick(View v) {
            containerFragment.onModifyOfferClicked(offersSentList.get(position));
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

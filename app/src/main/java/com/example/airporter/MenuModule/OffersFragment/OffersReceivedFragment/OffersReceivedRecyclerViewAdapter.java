package com.example.airporter.MenuModule.OffersFragment.OffersReceivedFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.airporter.R;
import com.example.airporter.data.OffersReceived;

import java.util.ArrayList;

public class OffersReceivedRecyclerViewAdapter extends RecyclerView.Adapter<OffersReceivedRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<OffersReceived> offersReceivedList;
    private OffersReceivedFragment containerFragment;
    private Context context;

    public OffersReceivedRecyclerViewAdapter(ArrayList<OffersReceived> offersReceivedList, OffersReceivedFragment containerFragment) {
        this.offersReceivedList = offersReceivedList;
        this.containerFragment = containerFragment;
        this.context = containerFragment.getContext();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offers_received, parent, false);
        return new MyViewHolder(view, new OnViewClickListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OffersReceived offer = offersReceivedList.get(position);
        holder.onViewClickListener.setPosition(position);

        Glide.with(context.getApplicationContext()).load(offer.getProductImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.productImage);
        holder.productPrice.setText(offer.getPrice());
        holder.deliverBefore.setText(offer.getDeliverBefore());
        holder.deliverFrom.setText(offer.getDeliverFrom());
        holder.deliverTo.setText(offer.getDeliverTo());
        holder.productTitle.setText(offer.getProductName());

    }

    @Override
    public int getItemCount() {
        return offersReceivedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productTitle;
        TextView deliverFrom;
        TextView deliverTo;
        TextView deliverBefore;
        TextView productPrice;
        ImageView productImage;
        OnViewClickListener onViewClickListener;

        public MyViewHolder(@NonNull View itemView, OnViewClickListener onViewClickListener) {
            super(itemView);
            this.productTitle = itemView.findViewById(R.id.productTitleTextViewId);
            this.deliverFrom = itemView.findViewById(R.id.deliverFromTextViewId);
            this.deliverTo = itemView.findViewById(R.id.deliverToTextViewid);
            this.deliverBefore = itemView.findViewById(R.id.deliverBeforeTextViewId);
            this.productPrice = itemView.findViewById(R.id.productPriceTextViewId);
            this.productImage = itemView.findViewById(R.id.productImageViewId);
            this.onViewClickListener = onViewClickListener;
            itemView.setOnClickListener(onViewClickListener);
        }
    }

    private class OnViewClickListener implements View.OnClickListener{
        int position;

        @Override
        public void onClick(View v) {
            if(containerFragment!=null)
                containerFragment.onRecyclerViewClicked(offersReceivedList.get(position).getOrderId());
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

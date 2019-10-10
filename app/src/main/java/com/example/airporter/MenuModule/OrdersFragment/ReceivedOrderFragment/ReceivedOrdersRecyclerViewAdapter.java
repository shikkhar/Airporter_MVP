package com.example.airporter.MenuModule.OrdersFragment.ReceivedOrderFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.airporter.R;
import com.example.airporter.data.Order;

import java.util.ArrayList;

public class ReceivedOrdersRecyclerViewAdapter extends RecyclerView.Adapter<ReceivedOrdersRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Order> receivedOrderList;
    private ReceivedOrderFragment containerFragment;

    public ReceivedOrdersRecyclerViewAdapter(ArrayList<Order> receivedOrderList, ReceivedOrderFragment containerFragment) {
        this.receivedOrderList = receivedOrderList;
        this.containerFragment = containerFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_received_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = receivedOrderList.get(position);


        Glide.with(containerFragment.getContext().getApplicationContext()).load(order.getShopperImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.shopperImage);

        Glide.with(containerFragment.getContext().getApplicationContext()).load(order.getProductImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.productImage);

        holder.shopperNameTextView.setText(order.getShopperName());
        holder.productNameTextView.setText(order.getProductName());
        holder.deliverToTextView.setText(order.getDeliverTo());
        holder.deliverFromTextView.setText(order.getDeliverFrom());
        holder.deliverBeforeTextView.setText(order.getDeliverBefore());
        holder.productPriceTextView.setText(order.getPrice());
        holder.rewardTextView.setText(order.getReward());

        //double offerPrice =  Double.valueOf(order.getPrice()) + Double.valueOf(order.getReward());
        //holder.offerPriceTextView.setText(String.valueOf(offerPrice));
    }

    @Override
    public int getItemCount() {
        return receivedOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView shopperImage;
        private ImageView productImage;
        private TextView shopperNameTextView;
        private TextView productNameTextView;
        private TextView deliverToTextView;
        private TextView deliverFromTextView;
        private TextView deliverBeforeTextView;
        private TextView productPriceTextView;
        private TextView offerPriceTextView;
        private TextView rewardTextView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shopperImage = itemView.findViewById(R.id.displayImageViewId);
            this.productImage = itemView.findViewById(R.id.productImageViewId);
            this.shopperNameTextView = itemView.findViewById(R.id.shopperNameTextViewId);
            this.productNameTextView = itemView.findViewById(R.id.productTitleTextViewId);
            this.deliverToTextView = itemView.findViewById(R.id.deliverToTextViewid);
            this.deliverFromTextView = itemView.findViewById(R.id.deliverFromTextViewId);
            this.deliverBeforeTextView = itemView.findViewById(R.id.deliverBeforeTextViewId);
            this.productPriceTextView = itemView.findViewById(R.id.productPriceTextViewId);
            this.offerPriceTextView = itemView.findViewById(R.id.textViewOfferPriceId);
            this.rewardTextView = itemView.findViewById(R.id.textViewRewardId);
        }
    }
}
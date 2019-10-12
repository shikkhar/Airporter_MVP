package com.example.airporter.MenuModule.OrdersFragmentModule.RequestedOrderFragmentModule;

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

public class RequestedOrdersRecyclerViewAdapter extends RecyclerView.Adapter<RequestedOrdersRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Order> requestedOrderList;
    private RequestedOrderFragment containerFragment;

    public RequestedOrdersRecyclerViewAdapter(ArrayList<Order> requestedOrderList, RequestedOrderFragment containerFragment) {
        this.requestedOrderList = requestedOrderList;
        this.containerFragment = containerFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_requested_orders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order = requestedOrderList.get(position);

        Glide.with(containerFragment.getContext().getApplicationContext()).load(order.getProductImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(holder.productImage);
        holder.productPrice.setText(order.getPrice());
        holder.deliverBefore.setText(order.getDeliverBefore());
        holder.deliverFrom.setText(order.getDeliverFrom());
        holder.deliverTo.setText(order.getDeliverTo());
        holder.productTitle.setText(order.getProductName());
    }

    @Override
    public int getItemCount() {
        return requestedOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView labelClickToSee;
        private TextView productTitle;
        private TextView deliverFrom;
        private TextView deliverTo;
        private TextView deliverBefore;
        private TextView productPrice;
        private ImageView productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productTitle = itemView.findViewById(R.id.productTitleTextViewId);
            this.deliverFrom = itemView.findViewById(R.id.deliverFromTextViewId);
            this.deliverTo = itemView.findViewById(R.id.deliverToTextViewid);
            this.deliverBefore = itemView.findViewById(R.id.deliverBeforeTextViewId);
            this.productPrice = itemView.findViewById(R.id.productPriceTextViewId);
            this.productImage = itemView.findViewById(R.id.productImageViewId);
            this.labelClickToSee = itemView.findViewById(R.id.labelClickToSeeOffers);
            this.labelClickToSee.setVisibility(View.GONE);
        }
    }
}

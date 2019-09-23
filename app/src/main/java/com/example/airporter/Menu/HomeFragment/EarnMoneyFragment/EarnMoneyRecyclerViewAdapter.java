package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.airporter.R;
import com.example.airporter.data.Order;

import java.util.List;

public class EarnMoneyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Order> orderList;
    private Context mContext;

    public EarnMoneyRecyclerViewAdapter(List<Order> orderList, Context mContext) {
        this.orderList = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_earn_money, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        ((MyViewHolder) holder).shopperName.setText(order.getShopperName());
        ((MyViewHolder) holder).productTitle.setText(order.getProductName());
        ((MyViewHolder) holder).deliverFrom.setText(order.getDeliverFrom());
        ((MyViewHolder) holder).deliverTo.setText(order.getDeliverTo());
        ((MyViewHolder) holder).deliverBefore.setText(order.getDeliverBefore());
        ((MyViewHolder) holder).productPrice.setText(order.getPrice());
        Glide.with(mContext)
                .load(order.getProductImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(((MyViewHolder)holder).productImage);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //private TextView orderId;
        public TextView shopperName;
        public TextView productTitle;
        public TextView deliverFrom;
        public TextView deliverTo;
        public TextView deliverBefore;
        public TextView productPrice;
        public ImageView productImage;
        //private TextView orderDateTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopperName = itemView.findViewById(R.id.shopperNametextViewId);
            productTitle = itemView.findViewById(R.id.productTitleTextViewId);
            deliverFrom = itemView.findViewById(R.id.deliverfromTextViewId);
            deliverTo = itemView.findViewById(R.id.deliverToTextViewid);
            deliverBefore = itemView.findViewById(R.id.deliverBeforeTextViewId);
            productPrice = itemView.findViewById(R.id.productPriceTextViewId);
            productImage = itemView.findViewById(R.id.productImageViewId);
        }
    }
}

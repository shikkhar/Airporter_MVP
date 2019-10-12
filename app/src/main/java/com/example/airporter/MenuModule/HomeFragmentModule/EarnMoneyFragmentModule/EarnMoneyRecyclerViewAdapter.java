package com.example.airporter.MenuModule.HomeFragmentModule.EarnMoneyFragmentModule;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.airporter.R;
import com.example.airporter.data.Order;

import java.util.List;

public class EarnMoneyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "EarnMoneyRecyclerView";
    private List<Order> orderList;
    private EarnMoneyFragment containerFragment;
    private Context mContext;
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_ITEM = 2;


    public EarnMoneyRecyclerViewAdapter(List<Order> orderList, EarnMoneyFragment containerFragment) {
        this.orderList = orderList;
        this.containerFragment = containerFragment;
        this.mContext = containerFragment.getContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        switch (viewType) {

            case VIEW_TYPE_ITEM:
                view = inflater.inflate(R.layout.list_item_earn_money, parent, false);
                return new MyViewHolder(view, new OnRewardTextChangeWatcher(), new OnSubmitClickListener());

            case VIEW_TYPE_FOOTER:
                view = inflater.inflate(R.layout.footer_view_recycler_view_earn_money, parent, false);
                return new FooterViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                MyViewHolder myViewHolder = null;
                try {
                    myViewHolder = (MyViewHolder) holder;
                } catch (ClassCastException e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                }
                if (!orderList.isEmpty() && myViewHolder != null) {
                    Order order = orderList.get(position);
                    myViewHolder.rewardTextChangeWatcher.setPosition(position);
                    myViewHolder.submitClickListener.setPosition(position);

                    Glide.with(mContext.getApplicationContext())
                            .load(order.getProductImagePath())
                            .centerCrop()
                            .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                            .error(R.drawable.cancel_offer_36dp)
                            .into(myViewHolder.productImage);

                    Glide.with(mContext.getApplicationContext())
                            .load(order.getShopperImagePath())
                            .centerCrop()
                            .placeholder(R.drawable.default_userimage_xhdpi_48dp)
                            .error(R.drawable.cancel_offer_36dp)
                            .into(myViewHolder.shopperImage);



                    myViewHolder.shopperName.setText(order.getShopperName());
                    myViewHolder.productTitle.setText(order.getProductName());
                    myViewHolder.deliverFrom.setText(order.getDeliverFrom());
                    myViewHolder.deliverTo.setText(order.getDeliverTo());
                    myViewHolder.deliverBefore.setText(order.getDeliverBefore());
                    myViewHolder.productPrice.setText(order.getPrice());
                    myViewHolder.rewardEditText.setText(order.getReward());

                }
                break;

            case VIEW_TYPE_FOOTER:
                FooterViewHolder footerViewHolder = null;
                try {
                    footerViewHolder = (FooterViewHolder) holder;
                } catch (ClassCastException e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                }

                footerViewHolder.loadMoreImageButton.setOnClickListener(new OnLoadMoreClickListener());
                break;
        }


    }

    @Override
    public int getItemCount() {
        //we add 1 to add space for the  footer view
        return orderList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;

    }

    private  class OnRewardTextChangeWatcher implements TextWatcher{
        int position;


        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            orderList.get(position).setReward(s.toString());
        }
    }

    private class OnLoadMoreClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            containerFragment.onLoadMoreClick();
        }
    }

    private class OnSubmitClickListener implements View.OnClickListener {
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            containerFragment.onSubmitClick(position, orderList.get(position).getReward());
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private ImageButton loadMoreImageButton;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            loadMoreImageButton = itemView.findViewById(R.id.loadMoreImageButtonId);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //private TextView orderId;
        TextView shopperName;
        TextView productTitle;
        TextView deliverFrom;
        TextView deliverTo;
        TextView deliverBefore;
        TextView productPrice;
        ImageView productImage;
        Button submitOfferButton;
        EditText rewardEditText;
        ImageView shopperImage;
        OnRewardTextChangeWatcher rewardTextChangeWatcher;
        OnSubmitClickListener submitClickListener;
        //private TextView orderDateTime;

        public MyViewHolder(@NonNull View itemView,
                            OnRewardTextChangeWatcher rewardTextChangeWatcher,
                            OnSubmitClickListener submitClickListener) {
            super(itemView);
            shopperName = itemView.findViewById(R.id.shopperNameTextViewId);
            productTitle = itemView.findViewById(R.id.productTitleTextViewId);
            deliverFrom = itemView.findViewById(R.id.deliverFromTextViewId);
            deliverTo = itemView.findViewById(R.id.deliverToTextViewid);
            deliverBefore = itemView.findViewById(R.id.deliverBeforeTextViewId);
            productPrice = itemView.findViewById(R.id.productPriceTextViewId);
            productImage = itemView.findViewById(R.id.productImageViewId);
            submitOfferButton = itemView.findViewById(R.id.submitOfferButtonId);
            rewardEditText = itemView.findViewById(R.id.rewardEditTextId);
            shopperImage = itemView.findViewById(R.id.displayImageViewId);
            this.rewardTextChangeWatcher = rewardTextChangeWatcher;
            this.submitClickListener = submitClickListener;
            rewardEditText.addTextChangedListener(rewardTextChangeWatcher);
            submitOfferButton.setOnClickListener(submitClickListener);
        }
    }

}

package com.example.airporter.MenuModule.MessagesFragmentModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airporter.AppController;
import com.example.airporter.R;
import com.example.airporter.data.Messages;

import java.util.ArrayList;

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Messages> messagesList;
    private MessagesFragment containerFragment;
    String userId = AppController.getInstance().getPreferenceManager().getUserId();

    public MessagesRecyclerViewAdapter(ArrayList<Messages> messagesList, MessagesFragment containerFragment) {
        this.messagesList = messagesList;
        this.containerFragment = containerFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_messages, parent, false);

        return new MyViewHolder(view, new OnItemClickListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Messages message = messagesList.get(position);
        holder.onItemClickListener.setPosition(position);
        holder.orderIdTextView.setText(message.getOrderId());
        if(userId.equals(messagesList.get(position).getBidderId()))
            holder.receiverNameTextView.setText(messagesList.get(position).getShopperName());
        else
            holder.receiverNameTextView.setText(messagesList.get(position).getBidderName());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private OnItemClickListener onItemClickListener;
        private TextView orderIdTextView;
        private TextView receiverNameTextView;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            this.orderIdTextView = itemView.findViewById(R.id.textViewOrderId);
            this.receiverNameTextView = itemView.findViewById(R.id.textViewReceiverNameId);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

    private class OnItemClickListener implements View.OnClickListener{

        private int position;

        @Override
        public void onClick(View v) {
            containerFragment.onRecyclerViewItemClicked(position);
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

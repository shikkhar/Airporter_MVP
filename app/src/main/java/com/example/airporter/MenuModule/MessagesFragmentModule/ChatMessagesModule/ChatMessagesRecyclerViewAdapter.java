package com.example.airporter.MenuModule.MessagesFragmentModule.ChatMessagesModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airporter.AppController;
import com.example.airporter.R;
import com.example.airporter.data.ChatMessage;

import java.util.ArrayList;

public class ChatMessagesRecyclerViewAdapter extends RecyclerView.Adapter<ChatMessagesRecyclerViewAdapter.MyViewHolder> {

    public static final int LEFT_ALIGNED = 0;
    public static final int RIGHT_ALIGNED = 1;

    private ArrayList<ChatMessage> chatMessageList;
    private String userId = AppController.getInstance().getPreferenceManager().getUserId();

    public ChatMessagesRecyclerViewAdapter(ArrayList<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        switch(viewType){
            case LEFT_ALIGNED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_message_left_aligned, parent, false);
                break;
            case RIGHT_ALIGNED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_message_right_aligned, parent, false);
                break;
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        holder.chatMessageTextView.setText(chatMessage.getChatMessage());
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        String senderId = chatMessage.getSenderId();

        if(userId.equals(senderId))
            return RIGHT_ALIGNED;
        else
            return LEFT_ALIGNED;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView chatMessageTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chatMessageTextView = itemView.findViewById(R.id.textViewChatMessageId);
        }
    }
}

package com.example.airporter.Menu.MessagesFragment;

import com.example.airporter.data.Messages;

import java.util.ArrayList;

public interface MessagesMvpContract {
    void fetchMessages();
    interface MessagesView{
        void onMessagesFetched(ArrayList<Messages> messageList);
    }
}

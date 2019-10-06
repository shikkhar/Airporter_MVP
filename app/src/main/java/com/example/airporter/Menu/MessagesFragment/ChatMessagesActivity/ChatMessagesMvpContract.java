package com.example.airporter.Menu.MessagesFragment.ChatMessagesActivity;

import com.example.airporter.data.ChatMessage;

import java.util.ArrayList;

public interface ChatMessagesMvpContract {

    void fetchChatMessages(String orderId, String bidderId);

    void uploadChatMessage(String orderId, String messageToSend, String bidderId);

    void fetchFCMToken(String receiverId);

    void sendChatMessage(String fcmToken, String senderName, String messageToSend, String bidderId, String shopperId);

    interface ChatMessagesView{
        void onChatMessagesFetched(ArrayList<ChatMessage> chatMessageList);

        void onChatMessageUploaded(ChatMessage chatMessage);

        void onFcmTokenFetched(String token);

        void onChatMessageSent();
    }
}

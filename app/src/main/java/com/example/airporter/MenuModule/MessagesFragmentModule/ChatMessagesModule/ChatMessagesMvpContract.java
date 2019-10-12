package com.example.airporter.MenuModule.MessagesFragmentModule.ChatMessagesModule;

import com.example.airporter.data.ChatMessage;
import com.example.airporter.data.Messages;

import java.util.ArrayList;

public interface ChatMessagesMvpContract {

    void fetchChatMessages(String orderId, String bidderId);

    void uploadChatMessage(String orderId, String messageToSend, String bidderId);

    void fetchFCMToken(String receiverId);

    void sendChatMessage(String fcmToken, String senderName, String messageToSend, Messages message);

    interface ChatMessagesView{
        void onChatMessagesFetched(ArrayList<ChatMessage> chatMessageList);

        void onChatMessageUploaded(ChatMessage chatMessage);

        void onFcmTokenFetched(String token);

        void onChatMessageSent();
    }
}

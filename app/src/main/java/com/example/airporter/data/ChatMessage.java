package com.example.airporter.data;

public class ChatMessage {

    private String chatMessage;
    private String bidderId;
    private  String shopperId;
    private String senderId;

    public ChatMessage(String chatMessage, String bidderId, String shopperId, String senderId) {
        this.chatMessage = chatMessage;
        this.bidderId = bidderId;
        this.shopperId = shopperId;
        this.senderId = senderId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getBidderId() {
        return bidderId;
    }

    public String getShopperId() {
        return shopperId;
    }

    public String getSenderId() {
        return senderId;
    }
}

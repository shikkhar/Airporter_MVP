package com.example.airporter.MenuModule;

public interface MenuMvpContract {
    void updateFcmToken(String token);
    interface MenuView{
        void onFcmTokenUpdateCompleted(boolean isSuccessful);
    }
}

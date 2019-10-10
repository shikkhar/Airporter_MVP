package com.example.airporter.MainModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.airporter.AppController;
import com.example.airporter.LoginModule.LoginActivity;
import com.example.airporter.MenuModule.MenuActivity;
import com.example.airporter.R;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.MainActivityView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout for the activity
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new MyRunnable(), 2000);
    }

    private class MyRunnable implements Runnable{

        String userId = AppController.getInstance().getPreferenceManager().getUserId();
        String fcmToken = AppController.getInstance().getPreferenceManager().getFcmToken();
        @Override

        public void run() {
            if(userId != null && fcmToken != null){
                Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(menuIntent);
                finish();
            } else {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }
    }
}

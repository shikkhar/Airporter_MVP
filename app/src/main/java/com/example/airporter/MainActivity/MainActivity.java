package com.example.airporter.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airporter.AppController;
import com.example.airporter.LoginActivity.LoginActivity;
import com.example.airporter.Menu.MenuActivity;
import com.example.airporter.R;
import com.example.airporter.helper.AirporterPreferenceManager;
import com.example.airporter.helper.ApiRequestManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.MainActivityView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout for the activity
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new MyRunnable(), 3000);
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

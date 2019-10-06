package com.example.airporter.LoginActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airporter.AppController;
import com.example.airporter.Menu.MenuActivity;
import com.example.airporter.R;
import com.example.airporter.helper.AirporterPreferenceManager;
import com.example.airporter.helper.ApiRequestManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginActivityPresenter.View {

    //declare the member
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Dialog mOverlayDialog;
    private ProgressBar progressBar;
    private LoginButton fbLoginButton;
    /*create an object of the presenter class
    all non ui related actions are delegated to this class*/
    private LoginActivityPresenter loginActivityPresenter;
    private CallbackManager fbCallbackManager;

    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";

    @Override
    protected void onStart() {
        super.onStart();
        fbLoginButton.registerCallback(fbCallbackManager, new LoginActivity.FbLoginCallback());
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout for the activity
        setContentView(R.layout.activity_login);

        /*create an object of the presenter class
        all non ui related actions are delegated to this class*/
        loginActivityPresenter = new LoginActivityPresenter(this);
        fbCallbackManager = CallbackManager.Factory.create();
        //binding the views
        emailEditText = this.findViewById(R.id.signUpEmaileditTextId);
        passwordEditText = this.findViewById(R.id.signUpPasswordEditTextId);
        loginButton = this.findViewById(R.id.loginButtonId);
        progressBar = this.findViewById(R.id.signinProgressBarId);
        fbLoginButton = this.findViewById(R.id.fbLoginButton);
        fbLoginButton.setPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE));

        //bring focus to the email field
        emailEditText.requestFocus();

        //set text change listener
        emailEditText.addTextChangedListener(new LoginActivity.EmailTextWatcher());
        passwordEditText.addTextChangedListener(new LoginActivity.PasswordTextWatcher());

        //setting focus change listeners
        emailEditText.setOnFocusChangeListener(new LoginActivity.OnEmailFocusChangeListener());
        passwordEditText.setOnFocusChangeListener(new LoginActivity.OnPasswordFocusChangeListener());

        //set click listener on Login Button
        loginButton.setOnClickListener(new LoginActivity.OnLoginButtonClickListener());
    }

    //use this code when using a custom button for fb login
   /* private class OnFbLoginButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
        }
    }*/

    private class OnLoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //display an invisible overlay dialog to prevent user interaction and pressing back
            if (mOverlayDialog == null)
                mOverlayDialog = new Dialog(LoginActivity.this, android.R.style.Theme_Panel);
            mOverlayDialog.setCancelable(false);
            mOverlayDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginActivityPresenter.authenticateLogin(email, password, ApiRequestManager.getInstance());
        }
    }

    private static class FbLoginCallback implements FacebookCallback<LoginResult> {
        private ProfileTracker profileTracker;
        @Override
        public void onSuccess(LoginResult loginResult) {
            if(Profile.getCurrentProfile()==null){
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        String name = currentProfile.getFirstName();
                        Uri email = currentProfile.getProfilePictureUri(80, 80);
                        profileTracker.stopTracking();
                    }
                };
            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    }

    private class EmailTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            loginActivityPresenter.checkEmailValid(s.toString().trim());
        }

    }

    private class OnEmailFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String email = emailEditText.getText().toString().trim();
                loginActivityPresenter.checkEmailValid(email);
            }
        }
    }

    private class PasswordTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            loginActivityPresenter.checkPasswordValid(s.toString().trim());
        }

    }

    private class OnPasswordFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String password = passwordEditText.getText().toString().trim();
                loginActivityPresenter.checkPasswordValid(password);
            }
        }
    }

    @Override
    public void setEmailEditTextError(String error) {
        emailEditText.setError(error);
    }

    @Override
    public void setPasswordEditTextError(String error) {
        passwordEditText.setError(error);
    }

    @Override
    public void setLoginButtonState(boolean isEmailValid, boolean isPasswordValid) {

        if (isEmailValid && isPasswordValid)
            loginButton.setEnabled(true);
        else
            loginButton.setEnabled(false);
    }


    @Override
    public void onLoginUpdate(boolean isLoginSuccessful, String userId) {

        if (isLoginSuccessful && userId != null) {
            AirporterPreferenceManager preferenceManager = AppController.getInstance().getPreferenceManager();
            preferenceManager.storeUserId(userId);
            loginActivityPresenter.getFcmToken();


        } else {
            mOverlayDialog.dismiss();
            progressBar.setVisibility(View.GONE);

            String displayMessage = getApplicationContext().getString(R.string.failed_login);
            Toast.makeText(getApplicationContext(), displayMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFcmTokenFetched(String token) {
        loginActivityPresenter.storeFcmToken(token);
    }


    @Override
    public void onFcmTokenStore(boolean isSuccesful) {
        mOverlayDialog.dismiss();
        progressBar.setVisibility(View.GONE);
        if(isSuccesful) {
            Intent menuIntent = new Intent(this, MenuActivity.class);
            startActivity(menuIntent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fbLoginButton.unregisterCallback(fbCallbackManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginActivityPresenter.onDestroy();
    }
}

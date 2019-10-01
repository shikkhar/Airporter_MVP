package com.example.airporter.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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
import com.example.airporter.helper.ApiRequestManager;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    //decalre the member
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Dialog mOverlayDialog;
    private ProgressBar progressBar;
    /*create an object of the presenter class
    all non ui related actions are delgated to this class*/
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout for the activity
        setContentView(R.layout.activity_main);

        /*create an object of the presenter class
        all non ui related actions are delegated to this class*/
        mainActivityPresenter = new MainActivityPresenter(this);

        //binding the views
        emailEditText = this.findViewById(R.id.signUpEmaileditTextId);
        passwordEditText = this.findViewById(R.id.signUpPasswordEditTextId);
        loginButton = this.findViewById(R.id.loginButtonId);
        progressBar = this.findViewById(R.id.signinProgressBarId);

        //bring focus to the email field
        emailEditText.requestFocus();

        //set text change listener
        emailEditText.addTextChangedListener(new EmailTextWatcher());
        passwordEditText.addTextChangedListener(new PasswordTextWatcher());

        //setting focus change listeners
        emailEditText.setOnFocusChangeListener(new OnEmailFocusChangeListener());
        passwordEditText.setOnFocusChangeListener(new OnPasswordFocusChangeListener());

        //set click listener on Login Button
        loginButton.setOnClickListener(new OnLoginButtonClickListener());
    }

    private class OnLoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //display an invisible overlay dialog to prevent user interaction and pressing back
            if (mOverlayDialog == null)
                mOverlayDialog = new Dialog(MainActivity.this, android.R.style.Theme_Panel);
            mOverlayDialog.setCancelable(false);
            mOverlayDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            mainActivityPresenter.authenticateLogin(email, password, ApiRequestManager.getInstance());
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
            mainActivityPresenter.checkEmailValid(s.toString().trim());
        }

    }

    private class OnEmailFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String email = emailEditText.getText().toString().trim();
                mainActivityPresenter.checkEmailValid(email);
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
            mainActivityPresenter.checkPasswordValid(s.toString().trim());
        }

    }

    private class OnPasswordFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String password = passwordEditText.getText().toString().trim();
                mainActivityPresenter.checkPasswordValid(password);
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

        mOverlayDialog.dismiss();
        progressBar.setVisibility(View.GONE);

        if (isLoginSuccessful && userId != null) {
            AppController.getInstance().getPreferenceManager().storeUserId(userId);
            Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(menuIntent);
            finish();
        } else {
            String displayMessage = getApplicationContext().getString(R.string.failed_login);
            Toast.makeText(getApplicationContext(), displayMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onDestroy();
    }
}

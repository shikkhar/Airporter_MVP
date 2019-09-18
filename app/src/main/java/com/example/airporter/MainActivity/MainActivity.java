package com.example.airporter.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.airporter.AppController;
import com.example.airporter.R;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Dialog mOverlayDialog;
    private ProgressBar progressBar;
    private MainActivityPresenter mainActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenter = new MainActivityPresenter(this);

        //binding the views
        emailEditText = this.findViewById(R.id.signUpEmaileditTextId);
        passwordEditText = this.findViewById(R.id.signUpPasswordEditTextId);
        loginButton = this.findViewById(R.id.loginButtonId);
        progressBar = this.findViewById(R.id.signinProgressBarId);

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


    @Override
    public void setLoginButtonState(boolean isEmailValid, boolean isPasswordValid) {
        if (isEmailValid && isPasswordValid)
            loginButton.setEnabled(true);
        else {
            if (!isEmailValid)
                emailEditText.setError("Invalid Email");
            if (!isPasswordValid)
                passwordEditText.setError("Invalid Password");
        }
    }

    @Override
    public void onLoginUpdate(boolean isLoginSuccessful) {
        if(isLoginSuccessful){
            String displayMessage = "Success";
            Toast.makeText(AppController.getContext(), displayMessage, Toast.LENGTH_SHORT).show();
        }

        else if(!isLoginSuccessful){
            String displayMessage = AppController.getContext().getString(R.string.failedLogin);
            Toast.makeText(AppController.getContext(), displayMessage, Toast.LENGTH_SHORT).show();
        }

        mOverlayDialog.dismiss();
        progressBar.setVisibility(View.GONE);
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
            String email = emailEditText.getText().toString().trim();
            mainActivityPresenter.checkEmailValid(email);
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
            String password = passwordEditText.getText().toString().trim();
            mainActivityPresenter.checkPasswordValid(password);
        }
    }

    private  class OnLoginButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //display an invisible overlay dialog to prevent user interaction and pressing back
            if(mOverlayDialog == null)
                mOverlayDialog = new Dialog(MainActivity.this, android.R.style.Theme_Panel);
            mOverlayDialog.setCancelable(false);
            mOverlayDialog.show();
            progressBar.setVisibility(View.VISIBLE);
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            mainActivityPresenter.authenticateLogin(email, password);
        }
    }
}

package com.example.airporter.MainActivity;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;

import com.android.volley.VolleyError;
import com.example.airporter.AppController;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.MyHash;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivityPresenter {
    private View mView;
    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    public static final String TAG = "MainActivityPresenter";

    public MainActivityPresenter(View mView) {
        this.mView = mView;
    }

    public void onDestroy(){
        AppController.getInstance().getRequestQueue().cancelAll(CONSTANTS.NetworkRequestTags.LOGIN_WITH_EMAIL);
        mView = null;
    }

    public interface View {
        void setLoginButtonState(boolean isEmailValid, boolean isPasswordValid);

        void setEmailEditTextError(String error);

        void setPasswordEditTextError(String error);

        void onLoginUpdate(boolean isLoginSuccessful, String userId);
    }


    public void checkEmailValid(@NonNull String email) {
        if (email.isEmpty()) {
            mView.setEmailEditTextError(CONSTANTS.DisplayMessages.ERROR_EMAIL_EMPTY);
            isEmailValid = false;
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            mView.setEmailEditTextError(CONSTANTS.DisplayMessages.ERROR_EMAIL_INVALID);
            isEmailValid = false;
        } else
            isEmailValid = true;

        mView.setLoginButtonState(isEmailValid, isPasswordValid);
    }

    public void checkPasswordValid(@NonNull String password) {
        if (password.isEmpty()) {
            isPasswordValid = false;
            mView.setPasswordEditTextError(CONSTANTS.DisplayMessages.ERROR_PASSWORD_EMPTY);
        } else
            isPasswordValid = true;

        mView.setLoginButtonState(isEmailValid, isPasswordValid);
    }

    public void authenticateLogin(String email, String password, ApiRequestManager apiRequestObject) {
        String encryptedPassword = MyHash.sha256(password);
        apiRequestObject.authenticateLogin(email, encryptedPassword, new AuthenticateLoginCallbackListener(mView), CONSTANTS.NetworkRequestTags.LOGIN_WITH_EMAIL);
    }

    private static class AuthenticateLoginCallbackListener implements VolleySeverRequest.VolleyResponseCallback {
        private WeakReference<View> mView;

        public AuthenticateLoginCallbackListener(View mView) {
            this.mView = new WeakReference<>(mView);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                String result = response.getString("success");
                View mainActivityView = mView.get();

                if (mainActivityView != null) {
                    if (result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)) {
                        JSONArray data = response.getJSONArray("data");
                        String userId = data.getJSONObject(0).getString("userId");
                        mainActivityView.onLoginUpdate(true, userId);
                    } else
                        mainActivityView.onLoginUpdate(false,null);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            Log.d(TAG, "onFail: " + error.getMessage());
            View mainActivityView = mView.get();
            if (mainActivityView != null)
                mainActivityView.onLoginUpdate(false, null);
        }
    }
}

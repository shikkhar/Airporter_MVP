package com.example.airporter.MainActivity;

import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.core.util.PatternsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.airporter.AppController;
import com.example.airporter.R;
import com.example.airporter.helper.ApiRequests;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.MyHash;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MainActivityPresenter {
    private View mView;
    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    public static final String TAG = "MainActivityPresenter";

    public MainActivityPresenter(View mView) {
        this.mView = mView;
    }

    public void authenticateLogin(String email, String password, ApiRequests apiRequestObject){
        String encryptedPassword = MyHash.sha256(password);
        apiRequestObject.authenticateLogin(email, encryptedPassword, new AuthenticateLoginCallbackListener(mView));
    }

    public void checkEmailValid(String email, int viewId) {
        if (PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty())
            isEmailValid = true;
        else
            isEmailValid = false;

        mView.setLoginButtonState(isEmailValid, isPasswordValid, viewId);
    }

    public void checkPasswordValid(String password, int viewId) {
        if (!password.isEmpty())
            isPasswordValid = true;
        else
            isPasswordValid = false;

        mView.setLoginButtonState(isEmailValid, isPasswordValid, viewId);
    }


    public interface View {
        void setLoginButtonState(boolean isEmailValid, boolean isPasswordValid, int viewId);
        void onLoginUpdate(boolean isLoginSuccessful);
    }

    private static class AuthenticateLoginCallbackListener implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<View> mView;

        public AuthenticateLoginCallbackListener(View mView) {
            this.mView = new WeakReference<>(mView);
        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                String result = response.getString("success");
                View mainActivityView = mView.get();

                if (result.equals(AppController.getContext().getString(R.string.yes_value))) {
                    JSONArray data = response.getJSONArray("data");
                    String userId = data.getJSONObject(0).getString("userId");
                    AppController.getInstance().getPreferenceManager().storeUserId(userId);
                    if(mainActivityView != null)
                        mainActivityView.onLoginUpdate(true);
                } else {
                    if(mainActivityView != null)
                        mainActivityView.onLoginUpdate(false);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            Log.d(TAG, "onFail: " + error.getMessage());
            View mainActivityView = mView.get();
            if(mainActivityView != null)
                mainActivityView.onLoginUpdate(true);
        }
    }
}

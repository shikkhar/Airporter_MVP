package com.example.airporter.LoginModule;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;

import com.android.volley.VolleyError;
import com.example.airporter.AppController;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.MyHash;
import com.example.airporter.helper.VolleySeverRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.STORE_FCM_TOKEN;

public class LoginActivityPresenter {
    private View mView;
    private boolean isEmailValid = false;
    private boolean isPasswordValid = false;
    public static final String TAG = "LoginActivityPresenter";

    public LoginActivityPresenter(View mView) {
        this.mView = mView;
    }

    public void onDestroy() {
        AppController.getInstance().getRequestQueue().cancelAll(CONSTANTS.NetworkRequestTags.LOGIN_WITH_EMAIL);
        mView = null;
    }

    public void storeFcmToken(String token) {
        if (token != null)
            ApiRequestManager.getInstance().storeFcmToken(token, new StoreFcmTokenCallback(mView), STORE_FCM_TOKEN);
    }

    public void getFcmToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnGetFcmTokenListener(mView));
    }


    public interface View {
        void setLoginButtonState(boolean isEmailValid, boolean isPasswordValid);

        void setEmailEditTextError(String error);

        void setPasswordEditTextError(String error);

        void onLoginUpdate(boolean isLoginSuccessful, String userId);

        void onFcmTokenFetched(String token);

        void onFcmTokenStore(boolean isSuccessful);
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

    private static class OnGetFcmTokenListener implements OnCompleteListener<InstanceIdResult>{
        private WeakReference<View> mView;

        public OnGetFcmTokenListener(View view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if(task.isSuccessful()) {
                String token = task.getResult().getToken();
                View view = mView.get();
                if(view!=null)
                    view.onFcmTokenFetched(token);
            }
        }
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
                View loginActivityView = mView.get();

                if (loginActivityView != null) {
                    if (result.equals(CONSTANTS.NetworRequestResponse.SUCCESS)) {
                        JSONArray data = response.getJSONArray("data");
                        String userId = data.getJSONObject(0).getString("userId");
                        loginActivityView.onLoginUpdate(true, userId);
                    } else
                        loginActivityView.onLoginUpdate(false, null);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFail(VolleyError error) {
            Log.d(TAG, "onFail: " + error.getMessage());
            View loginActivityView = mView.get();
            if (loginActivityView != null)
                loginActivityView.onLoginUpdate(false, null);
        }
    }

    private static class StoreFcmTokenCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<View> mView;

        public StoreFcmTokenCallback(View view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            View view = mView.get();
            if(view != null)
                view.onFcmTokenStore(true);
        }

        @Override
        public void onFail(VolleyError error) {
            View view = mView.get();
            if(view != null)
                view.onFcmTokenStore(false);
        }
    }
}

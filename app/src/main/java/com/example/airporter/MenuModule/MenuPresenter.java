package com.example.airporter.MenuModule;

import com.android.volley.VolleyError;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.STORE_FCM_TOKEN;

public class MenuPresenter implements MenuMvpContract {
    private MenuView mView;
    private ApiRequestManager apiRequestObj;

    public MenuPresenter(MenuView mView, ApiRequestManager apiRequestObj) {
        this.mView = mView;
        this.apiRequestObj = apiRequestObj;
    }

    @Override
    public void updateFcmToken(String token) {
        apiRequestObj.storeFcmToken(token, new StoreFcmTokenCallback(mView), STORE_FCM_TOKEN);
    }

    private static class StoreFcmTokenCallback implements VolleySeverRequest.VolleyResponseCallback{
        private WeakReference<MenuView> mView;

        public StoreFcmTokenCallback(MenuView view) {
            this.mView = new WeakReference<>(view);
        }

        @Override
        public void onSuccess(JSONObject response) {
            MenuView view = mView.get();
            if(view != null)
                view.onFcmTokenUpdateCompleted(true);
        }

        @Override
        public void onFail(VolleyError error) {
            MenuView view = mView.get();
            if(view != null)
                view.onFcmTokenUpdateCompleted(false);
        }
    }
}

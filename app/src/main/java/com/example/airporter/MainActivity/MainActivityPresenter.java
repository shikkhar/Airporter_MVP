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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import static com.example.airporter.helper.CONSTANTS.NetworkRequestTags.STORE_FCM_TOKEN;

public class MainActivityPresenter {
    private MainActivityView mView;

    interface MainActivityView{

    }

}

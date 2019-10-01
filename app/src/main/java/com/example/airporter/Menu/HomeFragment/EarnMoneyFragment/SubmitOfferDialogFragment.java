package com.example.airporter.Menu.HomeFragment.EarnMoneyFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.R;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitOfferDialogFragment extends DialogFragment{

private SubmitOfferDialogCallback submitOfferDialogCallback;

    public SubmitOfferDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String price = "0";
        String reward = "0";

        Bundle bundle = getArguments();
        if(bundle.containsKey("price") && bundle.containsKey("reward")) {
             price = bundle.getString("price");
             reward = bundle.getString("reward");
        }

        double offerPrice = Double.valueOf(price) + Double.valueOf(reward);

        onAttachToParentFragment(getParentFragment());
        return new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("Title")
                .setMessage("Submit Offer for " + String.valueOf(offerPrice))
                .setPositiveButton("Yes", new OnSubmitOfferClickListener(submitOfferDialogCallback))
                .setNegativeButton("No", new OnSubmitOfferClickListener(submitOfferDialogCallback))
                .create();
    }

    private void onAttachToParentFragment(Fragment parentFragment) {
        try {
            submitOfferDialogCallback = (SubmitOfferDialogCallback) parentFragment;
        }
        catch (ClassCastException e){
            throw new ClassCastException(
                    parentFragment.toString() + " must implement SubmitOfferDialogCallback");
        }
    }

    private static class OnSubmitOfferClickListener implements DialogInterface.OnClickListener{
        private WeakReference<SubmitOfferDialogCallback> submitOfferDialogCallback;

        public OnSubmitOfferClickListener(SubmitOfferDialogCallback submitOfferDialogCallback) {
            this.submitOfferDialogCallback = new WeakReference<>(submitOfferDialogCallback);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            SubmitOfferDialogCallback callback = submitOfferDialogCallback.get();
            if(callback != null)
                callback.onSubmitDialogButtonClick(which);
        }
    }
    public interface SubmitOfferDialogCallback{
        void onSubmitDialogButtonClick(int whichButton);
    }

}

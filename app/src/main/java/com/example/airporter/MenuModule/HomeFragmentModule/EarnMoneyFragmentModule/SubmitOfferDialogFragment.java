package com.example.airporter.MenuModule.HomeFragmentModule.EarnMoneyFragmentModule;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.airporter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitOfferDialogFragment extends DialogFragment {
    int position = -1;
    double offerPrice = 0;

    private SubmitOfferDialogCallback submitOfferDialogCallback;

    public SubmitOfferDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String price = "0";
        String reward = "0";

        Bundle bundle = getArguments();
        if (bundle.containsKey("price") && bundle.containsKey("reward") && bundle.containsKey("position")) {
            position = bundle.getInt("position");
            price = bundle.getString("price");
            reward = bundle.getString("reward");
        }

        offerPrice = Double.valueOf(price) + Double.valueOf(reward);

        onAttachToParentFragment(getParentFragment());

        return new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("Title")
                .setMessage("Submit Offer for " + String.valueOf(offerPrice))
                .setPositiveButton("Yes", new OnSubmitOfferClickListener())
                .setNegativeButton("No", new OnSubmitOfferClickListener())
                .create();
    }

    private void onAttachToParentFragment(Fragment parentFragment) {
        try {
            submitOfferDialogCallback = (SubmitOfferDialogCallback) parentFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    parentFragment.toString() + " must implement SubmitOfferDialogCallback");
        }
    }

    private class OnSubmitOfferClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String finalOfferPrice = String.valueOf(offerPrice);
            submitOfferDialogCallback.onSubmitDialogButtonClick(which, position, finalOfferPrice);
        }
    }

    public interface SubmitOfferDialogCallback {
        void onSubmitDialogButtonClick(int whichButton, int position, String offerPrice);
    }

}

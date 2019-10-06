package com.example.airporter.Menu.OffersFragment.OffersSentFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.airporter.Menu.HomeFragment.EarnMoneyFragment.SubmitOfferDialogFragment;
import com.example.airporter.R;
import com.example.airporter.data.OffersSent;
import com.example.airporter.data.Order;
import com.google.android.material.button.MaterialButton;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class ModifyOfferDialogFragment extends DialogFragment implements SubmitOfferDialogFragment.SubmitOfferDialogCallback {
    private static final String SUBMIT_OFFER_DIALOG_TAG = "0";
    private OffersSent offer;
    private SubmitOfferDialogFragment submitOfferDialogFragment;
    private ModifyOfferCallback modifyOfferCallback;
    private TextView shopperName;
    private TextView productTitle;
    private TextView deliverFrom;
    private TextView deliverTo;
    private TextView deliverBefore;
    private TextView productPrice;
    private ImageView productImage;
    private MaterialButton submitOfferButton;
    private EditText rewardEditText;
    private ImageView shopperImage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
        Bundle args = getArguments();
        if (args.containsKey("offer"))
            offer = args.getParcelable("offer");
    }

    private void onAttachToParentFragment(Fragment parentFragment) {
        try {
            modifyOfferCallback = (ModifyOfferCallback) parentFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(parentFragment.toString() + " must implement ModifyOfferCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.dialog_fragment_modify_offer, container, false);

        shopperName = itemView.findViewById(R.id.shopperNameTextViewId);
        productTitle = itemView.findViewById(R.id.productTitleTextViewId);
        deliverFrom = itemView.findViewById(R.id.deliverFromTextViewId);
        deliverTo = itemView.findViewById(R.id.deliverToTextViewid);
        deliverBefore = itemView.findViewById(R.id.deliverBeforeTextViewId);
        productPrice = itemView.findViewById(R.id.productPriceTextViewId);
        productImage = itemView.findViewById(R.id.productImageViewId);
        submitOfferButton = itemView.findViewById(R.id.submitOfferButtonId);
        rewardEditText = itemView.findViewById(R.id.rewardEditTextId);
        shopperImage = itemView.findViewById(R.id.displayImageViewId);

        submitOfferButton.setOnClickListener(new OnSubmitClickListener());

        Glide.with(getContext().getApplicationContext())
                .load(offer.getOrderImage())
                .centerCrop()
                .placeholder(R.drawable.default_productimage_xxhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(productImage);

        Glide.with(getContext().getApplicationContext())
                .load(offer.getShopperImagePath())
                .centerCrop()
                .placeholder(R.drawable.default_userimage_xhdpi_48dp)
                .error(R.drawable.cancel_offer_36dp)
                .into(shopperImage);

        shopperName.setText(offer.getShopperName());
        productTitle.setText(offer.getProductName());
        deliverFrom.setText(offer.getDeliverFrom());
        deliverTo.setText(offer.getDeliverTo());
        deliverBefore.setText(offer.getDeliverBefore());
        productPrice.setText(offer.getPrice());
        //rewardEditText.setText("");

        return itemView;
    }

    private class OnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String price = offer.getPrice();
            String reward = rewardEditText.getText().toString();
            FragmentManager fm = getChildFragmentManager();
            Fragment fragment = fm.findFragmentByTag(SUBMIT_OFFER_DIALOG_TAG);
            if (fragment != null)
                fm.beginTransaction().remove(fragment).commit();
            submitOfferDialogFragment = new SubmitOfferDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", -1);
            bundle.putString("price", price);
            bundle.putString("reward", reward);
            submitOfferDialogFragment.setArguments(bundle);
            submitOfferDialogFragment.show(fm, "dialog");
        }
    }

    @Override
    public void onSubmitDialogButtonClick(int whichButton, int position, String offerPrice) {
        String offerId = offer.getOfferId();
        submitOfferDialogFragment.dismiss();

        switch (whichButton) {
            case BUTTON_POSITIVE:
                modifyOfferCallback.updateOffer(offerId, offerPrice);
                break;

            case BUTTON_NEGATIVE:
                break;

            default:
        }
    }

    public interface ModifyOfferCallback {
        void updateOffer(String offerId, String offerPrice);
    }
}

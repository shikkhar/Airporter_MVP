package com.example.airporter.Menu.OrdersFragment.DeliveredOrderFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveredOrderFragment extends Fragment {


    public DeliveredOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivered_order, container, false);
    }

}
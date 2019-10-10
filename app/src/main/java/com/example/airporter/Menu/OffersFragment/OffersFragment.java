package com.example.airporter.Menu.OffersFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.Menu.OffersFragment.OffersReceivedFragment.OffersReceivedFragment;
import com.example.airporter.Menu.OffersFragment.OffersSentFragment.OffersSentFragment;
import com.example.airporter.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitlesList;
    private OffersFragmentPagerAdapter mAdapter;

    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentList();
        setupTitleList();
        mAdapter = new OffersFragmentPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragmentList, tabTitlesList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_offers, container, false);
        tabLayout = view.findViewById(R.id.tablayoutOffersFragmentId);
        viewPager = view.findViewById(R.id.viewPagerOffersFragmentId);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new OffersReceivedFragment());
        fragmentList.add(new OffersSentFragment());
    }

    private void setupTitleList() {
        tabTitlesList = new ArrayList<>();
        tabTitlesList.add(getString(R.string.tab_received_offers));
        tabTitlesList.add(getString(R.string.tab_sent_offers));
    }

}

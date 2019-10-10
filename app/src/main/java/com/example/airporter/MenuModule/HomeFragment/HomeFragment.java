package com.example.airporter.MenuModule.HomeFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.airporter.MenuModule.HomeFragment.EarnMoneyFragment.EarnMoneyFragment;
import com.example.airporter.MenuModule.HomeFragment.ShopFragment.ShopFragment;
import com.example.airporter.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ViewPager homeViewPager;
    private TabLayout homeTabLayout;
    private HomeFragmentPagerAdapter mAdapter;
    private List<Fragment> fragmentList;
    private List<String> tabTitles;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFragmentList();
        setupTabTitlesList();
        mAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragmentList, tabTitles);
    }

    private void setupFragmentList() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ShopFragment());
        fragmentList.add(new EarnMoneyFragment());
    }

    private void setupTabTitlesList() {
        tabTitles = new ArrayList<>();
        tabTitles.add(getString(R.string.tab_shop));
        tabTitles.add(getString(R.string.tab_earn));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewPager = view.findViewById(R.id.homevViewPagerId);
        homeTabLayout = view.findViewById(R.id.homeTabLayoutId);
        homeViewPager.setAdapter(mAdapter);
        homeTabLayout.setupWithViewPager(homeViewPager);
        return view;
    }

}

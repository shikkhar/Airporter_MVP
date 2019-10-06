package com.example.airporter.Menu.OrdersFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class OrderFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitleList;

    public OrderFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragmentList, ArrayList<String> tabTitleList) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.tabTitleList = tabTitleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

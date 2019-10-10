package com.example.airporter.MenuModule.OffersFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class OffersFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitlesList;

    public OffersFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fragmentList, ArrayList<String> tabTitlesList) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.tabTitlesList = tabTitlesList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitlesList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

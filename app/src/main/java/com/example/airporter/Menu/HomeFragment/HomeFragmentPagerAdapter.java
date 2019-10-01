package com.example.airporter.Menu.HomeFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

//TODO: Try switching super class to fragmentStatePagerAdapter for better memory utilization
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;
    private List<String> tabTitles;

    public HomeFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> fragmentList, List<String> tabTitles) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

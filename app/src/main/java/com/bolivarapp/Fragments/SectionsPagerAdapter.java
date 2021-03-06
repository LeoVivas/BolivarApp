package com.bolivarapp.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0)
            return PlaceholderFragment1.newInstance(i + 1);
        else
            return PlaceholderFragment2.newInstance(i + 1);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
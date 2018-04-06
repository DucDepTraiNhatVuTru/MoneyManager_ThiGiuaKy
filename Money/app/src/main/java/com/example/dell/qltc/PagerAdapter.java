package com.example.dell.qltc;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "tháng "+position+1;
    }

    @Override
    public int getCount() {
        return 6;
    }
}

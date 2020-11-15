package com.BaranovichiBus.barbus.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.BaranovichiBus.barbus.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionPagerAdapterBusStop extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;
    private boolean WhatTab;
    private Integer position;

    public SectionPagerAdapterBusStop(Context context, FragmentManager fm, boolean WhatTab, Integer position) {
        super(fm);
        mContext = context;
        this.WhatTab= WhatTab;
        this.position = position;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Tab4in tab4 = new Tab4in(mContext, WhatTab, this.position);
                return tab4;
            case 1:
                Tab5out tab5 = new Tab5out(mContext, WhatTab, this.position);
                return tab5;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }
}

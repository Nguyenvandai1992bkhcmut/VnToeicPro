package com.vntoeic.bkteam.vntoeicpro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dainguyen on 5/29/17.
 */

public class PageAdapterMain extends FragmentPagerAdapter {
    public PageAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FragmentAppBarMain fragmentAppBarMain = new FragmentAppBarMain();
        FragmentInfoUser fragmentInfoUser = new FragmentInfoUser();
        if(position==0)return fragmentInfoUser;
        else  return fragmentAppBarMain;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

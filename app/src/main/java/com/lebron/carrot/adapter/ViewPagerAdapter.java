package com.lebron.carrot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wuxiangkun on 2016/3/17.
 * Contacts by wuxiangkun@live.com
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList;
    private List<String> titleList;
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm);
        this.fragmentList = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

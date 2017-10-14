package com.audienl.superlibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/9/5.
 */
public class SuperFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();

    public SuperFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(Fragment... fragments) {
        mFragments = Arrays.asList(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

package com.jthou.wanandroid.util;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class AutoPlayAdapter extends PagerAdapter {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    public AutoPlayAdapter(ViewPager mViewPager, PagerAdapter mAdapter) {
        this.mViewPager = mViewPager;
        this.mAdapter = mAdapter;
        this.mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position = position % mAdapter.getCount();
        return mAdapter.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mAdapter.destroyItem(container, position, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = mViewPager.getCurrentItem();
        if (position == 0) {
            position = mAdapter.getCount();
            mViewPager.setCurrentItem(position, false);
        } else if (position == 100 - 1) {
            position = mAdapter.getCount() - 1;
            mViewPager.setCurrentItem(position, false);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int index = (mViewPager.getCurrentItem() + 1) % mAdapter.getCount();
            LogHelper.e("index : " + index);
            mViewPager.setCurrentItem(index);
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

}

package com.jthou.wanandroid.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class AutoPlayViewPager extends ViewPager {

    private Handler mHandler;

    public AutoPlayViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoPlayViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(getCurrentItem());
                return true;
            }
        });
        setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return false;
        });
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(new AutoPlayAdapter(adapter));
    }

    static class AutoPlayAdapter extends PagerAdapter {

        private PagerAdapter mAdapter;

        public AutoPlayAdapter(PagerAdapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return mAdapter.isViewFromObject(view, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return mAdapter.instantiateItem(container, position % mAdapter.getCount());
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mAdapter.destroyItem(container, position, object);
        }

    }

    public void startAutoPlay() {
        if (mHandler == null)
            mHandler = new AutoPlayHandler(this);
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    public void stopAutoPlay() {
        if (mHandler != null) mHandler.removeMessages(0);
    }

    static class AutoPlayHandler extends Handler {

        WeakReference<AutoPlayViewPager> mViewPager;

        public AutoPlayHandler(AutoPlayViewPager viewPager) {
            mViewPager = new WeakReference<>(viewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            AutoPlayViewPager viewPager = mViewPager.get();
            if (viewPager == null) return;
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1);
            sendEmptyMessageDelayed(0, 3000);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}

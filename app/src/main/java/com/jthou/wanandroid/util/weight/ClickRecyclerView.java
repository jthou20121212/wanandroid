package com.jthou.wanandroid.util.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ClickRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;

    public ClickRecyclerView(Context context) {
        this(context, null);
    }

    public ClickRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnItemTouchListener(this);
        mGestureDetector = new GestureDetectorCompat(getContext(), new OnItemGestureDetectorListener());
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {

        void onClick(RecyclerView.ViewHolder viewHolder);

    }

    private class OnItemGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = findChildViewUnder(e.getX(), e.getY());
            if(child != null) {
                RecyclerView.ViewHolder viewHolder = getChildViewHolder(child);
                mOnItemClickListener.onClick(viewHolder);
            }
            return true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
         mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}

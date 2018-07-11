package com.jthou.wanandroid.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by user on 2018/1/22.
 */

public class StickyTitleDecoration extends RecyclerView.ItemDecoration {

    private int mGroupHeight = 150;

    private SparseIntArray firstInGroupCash = new SparseIntArray(100);

    private Paint mTextPaint;
    private Paint mGroupPaint;

    private GroupNameCallback mGroupNameCallback;

    private StickyTitleDecoration() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#5b5d5c"));
        mTextPaint.setTextSize(45);

        mGroupPaint = new Paint();
        mGroupPaint.setAntiAlias(true);
        mGroupPaint.setColor(Color.WHITE);
    }

    public interface GroupNameCallback {

        String getGroupName(int position);

        void onGroupNameChange(int position);

    }

    public StickyTitleDecoration(GroupNameCallback mGroupName) {
        this();
        this.mGroupNameCallback = mGroupName;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mGroupNameCallback == null) return;
        int position = parent.getChildAdapterPosition(view);
        String groupName = mGroupNameCallback.getGroupName(position);
        // int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        if (isFirstLineInGroup(position, 1) && groupName != null) {
            //新group的第一行都需要留出空间
            outRect.top = mGroupHeight; //为悬浮view预留空间
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        String preGroupName;      // 标记上一个item对应的Group
        String curGroupName;      // 当前item对应的Group
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(childAt);
            curGroupName = mGroupNameCallback.getGroupName(position);
            if (i == 0) preGroupName = curGroupName;
            else preGroupName = mGroupNameCallback.getGroupName(position - 1);
            if (i == 0 || !TextUtils.equals(preGroupName, curGroupName)) {
                // 绘制悬浮
                int bottom = Math.max(mGroupHeight, (childAt.getTop() + parent.getPaddingTop()));//决定当前顶部第一个悬浮Group的bottom

                if (position + 1 < itemCount) {
                    //下一组的第一个View接近头部
                    int viewBottom = childAt.getBottom();
                    if (isLastLineInGroup(parent, position) && viewBottom < bottom) {
                        bottom = viewBottom;
                    }
                }

                c.drawRect(left, bottom - mGroupHeight, right, bottom, mGroupPaint);

                // 绘制组名
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float width = mTextPaint.measureText(curGroupName);
                c.drawText(curGroupName, (childAt.getWidth() - width) / 2, bottom - (mGroupHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.bottom, mTextPaint);

                LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
                int firstCompletelyVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (mGroupNameCallback != null)
                    mGroupNameCallback.onGroupNameChange(firstCompletelyVisibleItemPosition);
            }
        }
    }

    public void resetSpan(RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        if (recyclerView == null) {
            throw new NullPointerException("recyclerView not allow null");
        }
        if (gridLayoutManager == null) {
            throw new NullPointerException("gridLayoutManager not allow null");
        }
        final int spanCount = gridLayoutManager.getSpanCount();
        GridLayoutManager.SpanSizeLookup lookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span;
                String curGroupId = mGroupNameCallback.getGroupName(position);
                String nextGroupId;
                try {
                    //防止外面没判断，导致越界
                    nextGroupId = mGroupNameCallback.getGroupName(position + 1);
                } catch (Exception e) {
                    nextGroupId = curGroupId;
                }
                if (!TextUtils.equals(curGroupId, nextGroupId)) {
                    //为本行的最后一个
                    int posFirstInGroup = getFirstInGroupWithCash(position);
                    span = spanCount - (position - posFirstInGroup) % spanCount;
                } else {
                    span = 1;
                }
                return span;
            }
        };
        gridLayoutManager.setSpanSizeLookup(lookup);
    }

    // 判断是不是新组的第一行（GridLayoutManager使用）
    // 利用当前行的第一个对比前一个组名，判断当前是否为新组的第一样
    private boolean isFirstLineInGroup(int pos, int spanCount) {
        if (pos == 0) {
            return true;
        } else {
            int posFirstInGroup = getFirstInGroupWithCash(pos);
            if (pos - posFirstInGroup < spanCount) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected boolean isLastLineInGroup(RecyclerView recyclerView, int position) {
        String curGroupName = mGroupNameCallback.getGroupName(position);
        String nextGroupName;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        //默认往下查找的数量
        int findCount = 1;
        if (manager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) manager).getSpanCount();
            int firstPositionInGroup = getFirstInGroupWithCash(position);
            findCount = spanCount - (position - firstPositionInGroup) % spanCount;
        }
        try {
            nextGroupName = mGroupNameCallback.getGroupName(position + findCount);
        } catch (Exception e) {
            nextGroupName = curGroupName;
        }
        return !TextUtils.equals(curGroupName, nextGroupName);
    }

    // 拿到每组的第一项的索引，存在SparseIntArray中
    protected int getFirstInGroupWithCash(int position) {
        if (firstInGroupCash.get(position) == 0) {
            int firstPosition = getFirstInGroup(position);
            firstInGroupCash.put(position, firstPosition);
            return firstPosition;
        } else {
            return firstInGroupCash.get(position);
        }
    }

    // 拿到每组第一项的索引
    private int getFirstInGroup(int position) {
        if (position == 0) {
            return 0;
        } else {
            if (!TextUtils.equals(mGroupNameCallback.getGroupName(position), mGroupNameCallback.getGroupName(position - 1))) {
                return position;
            } else {
                return getFirstInGroup(position - 1);
            }
        }
    }

}

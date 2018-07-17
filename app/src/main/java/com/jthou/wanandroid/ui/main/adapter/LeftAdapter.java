package com.jthou.wanandroid.ui.main.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.util.L;

import java.util.List;

public class LeftAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {

    private int mSelectPosition;

    public LeftAdapter(int layoutResId, @Nullable List<Navigation> data) {
        super(layoutResId, data);
    }

    public void setSelectPosition(int position) {
        mSelectPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder holder, Navigation item) {
        holder.setText(R.id.tv_sort, item.getName());
        int position = mData.indexOf(item);
        if (position == mSelectPosition) {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.setTextColor(R.id.tv_sort, ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.deep_grey));
            holder.setTextColor(R.id.tv_sort, ContextCompat.getColor(mContext, R.color.shallow_grey));
        }
    }

}

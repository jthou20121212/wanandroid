package com.jthou.wanandroid.ui.main.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.model.entity.Navigation;

import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.SortHolder> {

    private Context mContext;
    private List<Navigation> mData;

    private int mSelectPosition;

    public LeftAdapter(Context context, List<Navigation> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @Override
    public SortHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_novigation_left, parent, false);
        return new SortHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SortHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getName());
        if (position == mSelectPosition) {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.deep_grey));
            holder.tvName.setTextColor(ContextCompat.getColor(mContext, R.color.shallow_grey));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setSelectPosition(int position) {
        mSelectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }

    class SortHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public SortHolder(View itemView) {
            super(itemView);
            this.tvName = itemView.findViewById(R.id.tv_sort);
//            this.tvName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("jthou", "单机点击进口量方式");
//                }
//            });
        }

    }
}

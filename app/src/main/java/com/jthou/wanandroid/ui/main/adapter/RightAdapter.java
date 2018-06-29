package com.jthou.wanandroid.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.util.CommonUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;


public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ClassifyHolder> {

    private Context mContext;
    private List<Navigation> mData;

    public RightAdapter(Context context, List<Navigation> mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @Override
    public ClassifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_navigation_right, parent, false);
        return new ClassifyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassifyHolder holder, int position) {
        List<Article> articles = mData.get(position).getArticles();
        TagFlowLayout flowLayout = (TagFlowLayout) holder.itemView;
        flowLayout.setAdapter(new TagAdapter<Article>(articles) {
            @Override
            public View getView(FlowLayout parent, int position, Article obj) {
                TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, flowLayout, false);
                if (obj == null) return null;
                textView.setText(obj.getTitle());
                textView.setTextColor(CommonUtils.randomColor());
                return textView;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra(Key.ARTICLE_LINK, articles.get(position).getLink());
                intent.putExtra(Key.ARTICLE_TITLE, articles.get(position).getTitle());
                mContext.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ClassifyHolder extends RecyclerView.ViewHolder {
        public ClassifyHolder(View itemView) {
            super(itemView);
        }
    }
}

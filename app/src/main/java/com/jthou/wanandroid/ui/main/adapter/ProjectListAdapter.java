package com.jthou.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.util.image.ImageLoader;

import java.util.List;

public class ProjectListAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public ProjectListAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        if (!TextUtils.isEmpty(item.getEnvelopePic())) {
            ImageLoader.displayImage(mContext, item.getEnvelopePic(), helper.getView(R.id.item_project_list_iv));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_project_list_title_tv, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDesc())) {
            helper.setText(R.id.item_project_list_content_tv, item.getDesc());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.item_project_list_time_tv, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.item_project_list_author_tv, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getApkLink())) {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_project_list_install_tv);
    }

}


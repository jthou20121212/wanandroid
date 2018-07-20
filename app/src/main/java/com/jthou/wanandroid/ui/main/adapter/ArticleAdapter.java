package com.jthou.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.model.entity.Article;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    // 标记是否是收藏页面
    private boolean isCollectPage;

    public void setCollectPage(boolean collectPage) {
        isCollectPage = collectPage;
    }

    public ArticleAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(article.getTitle()));
        }
        if (article.isCollect() || isCollectPage) {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_favorite);
        } else {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.item_search_pager_author, article.getAuthor());
        }
//        setTag(helper, article);
        // 收藏页面只有chapterName
        if(isCollectPage)
            helper.setText(R.id.item_search_pager_chapterName, article.getChapterName());
        else
            helper.setText(R.id.item_search_pager_chapterName, mContext.getString(R.string.chapter_name, article.getSuperChapterName(), article.getChapterName()));

        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.item_search_pager_niceDate, article.getNiceDate());
        }
//        if (isSearchPage) {
//            CardView cardView = helper.getView(R.id.item_search_pager_group);
//            cardView.setForeground(null);
//            if (isNightMode) {
//                cardView.setBackground(ContextCompat.getDrawable(mContext, R.color.card_color));
//            } else {
//                cardView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_search_item_bac));
//            }
//        }
    }

}

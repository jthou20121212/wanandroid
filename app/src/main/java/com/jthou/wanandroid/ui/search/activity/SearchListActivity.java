package com.jthou.wanandroid.ui.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.search.SearchContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.presenter.search.SearchPresenter;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.jthou.wanandroid.util.ItemClickSupport;
import com.jthou.wanandroid.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchListActivity extends BaseActivity<SearchPresenter> implements SearchContract.View, OnRefreshListener, OnLoadMoreListener, ItemClickSupport.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.id_tv_title)
    TextView mTvTitle;
    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_smartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<Article> mData;
    private ArticleAdapter mAdapter;

    private String mKeyword;
    private int mCurrentPage;

    private int mPosition;

    @Override
    protected int resource() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKeyword = getIntent().getStringExtra(Key.IT_KEYWORD);
        mTvTitle.setText(mKeyword);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(this);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimary), 1f);

        mData = new ArrayList<>();
        mAdapter = new ArticleAdapter(R.layout.item_article, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mPresenter.getSearchList(mCurrentPage, mKeyword);
    }

    @Override
    protected void onDestroy() {
        ItemClickSupport.removeFrom(mRecyclerView);
        super.onDestroy();
    }

    @Override
    public void showSearchResult(List<Article> articleList) {
        if (mCurrentPage == 0) {
            mAdapter.replaceData(articleList);
            mRefreshLayout.finishRefresh();
        } else {
            mAdapter.addData(articleList);
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void refreshCollectState(CollectEvent event) {
        if(mPosition == -1 || mPosition >= mData.size()) return;
        Article article = mData.get(mPosition);
        boolean collect = article.isCollect();
        if (collect == event.isCollect()) return;
        article.setCollect(event.isCollect());
        mAdapter.setData(mPosition, article);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getSearchList(mCurrentPage = 0, mKeyword);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getSearchList(++mCurrentPage, mKeyword);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Article article = mData.get(mPosition = position);
        intent.putExtra(Key.ARTICLE_LINK, article.getLink());
        intent.putExtra(Key.ARTICLE_TITLE, article.getTitle());
        intent.putExtra(Key.ARTICLE_ID, article.getId());
        intent.putExtra(Key.ARTICLE_IS_FAVORITE, article.isCollect());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}

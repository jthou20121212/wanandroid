package com.jthou.wanandroid.ui.main.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.FavoriteContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.presenter.main.FavoritePresenter;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2018/5/24.
 */

public class FavoriteFragment extends ParentFragment<FavoritePresenter> implements FavoriteContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    private BaseQuickAdapter mAdapter;
    private List<Article> mData;

    private boolean isRefresh;
    private int mCurrentPage;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    protected int resource() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initDataAndEvent() {
        mData = new ArrayList<>();
        mAdapter = new ArticleAdapter(R.layout.item_article, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mPresenter.getFavoriteArticleList(mCurrentPage = 0);

        showLoading();
    }

    @Override
    public void showFavoriteArticleList(List<Article> data) {
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();

        showNormal();

        if (isRefresh) {
            mAdapter.replaceData(data);
            isRefresh = false;
        } else {
            mAdapter.addData(data);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        mPresenter.getFavoriteArticleList(mCurrentPage = 0);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFavoriteArticleList(mCurrentPage++);
    }

    @Override
    public void reload() {
//        mPresenter.getFavoriteArticleList(mCurrentPage);
//
//        showLoading();

        mRefreshLayout.autoRefresh();
    }

}

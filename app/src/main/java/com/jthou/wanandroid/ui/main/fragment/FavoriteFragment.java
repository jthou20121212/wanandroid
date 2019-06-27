package com.jthou.wanandroid.ui.main.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.FavoriteContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.presenter.main.FavoritePresenter;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.activity.MainActivity;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.ItemClickSupport;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FavoriteFragment extends ParentFragment<FavoritePresenter> implements FavoriteContract.View, OnRefreshListener, OnLoadMoreListener, ItemClickSupport.OnItemClickListener {

    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)

    SmartRefreshLayout mRefreshLayout;

    private ArticleAdapter mAdapter;
    private List<Article> mData;

    private int mCurrentPage;

    private int mPosition = -1;

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
        mAdapter.setCollectPage(true);
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.empty_view, null);
        view.findViewById(R.id.id_tv_to).setOnClickListener(v -> {
            MainActivity activity = (MainActivity) _mActivity;
            activity.toHomePage();
        });
        mAdapter.setEmptyView(view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mPresenter.getFavoriteArticleList(mCurrentPage);

        showLoading();
    }

    @Override
    public void onDestroyView() {
        ItemClickSupport.removeFrom(mRecyclerView);
        super.onDestroyView();
    }

    @Override
    public void showFavoriteArticleList(List<Article> data) {
        if (mCurrentPage == 0) {
            mAdapter.replaceData(data);
            mRefreshLayout.finishRefresh();
            // 处理reload的情况
            mRefreshLayout.finishLoadMore();
        } else {
            if(data.size() > 0) {
                mAdapter.addData(data);
            } else {
                CommonUtils.showSnackMessage(_mActivity, getString(R.string.no_more));
            }
            mRefreshLayout.finishLoadMore();
        }

        showNormal();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFavoriteArticleList(mCurrentPage = 0);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getFavoriteArticleList(++mCurrentPage);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getFavoriteArticleList(mCurrentPage = 0);
    }

    @Override
    public void refreshCollectState(CollectEvent event) {
        if (event.isCollect()) return;
        if (mPosition == -1 || mPosition >= mData.size()) return;
        mAdapter.remove(mPosition);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
        Article article = mData.get(mPosition = position);
        intent.putExtra(Key.ARTICLE_LINK, article.getLink());
        intent.putExtra(Key.ARTICLE_TITLE, article.getTitle());
        intent.putExtra(Key.ARTICLE_ID, article.getId());
        intent.putExtra(Key.ARTICLE_IS_FAVORITE, true);
        startActivity(intent);
    }

}

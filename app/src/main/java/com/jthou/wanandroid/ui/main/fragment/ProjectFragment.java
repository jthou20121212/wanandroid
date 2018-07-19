package com.jthou.wanandroid.ui.main.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.ProjectContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.entity.ProjectClassify;
import com.jthou.wanandroid.presenter.main.ProjectPresenter;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.adapter.ProjectListAdapter;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.ItemClickSupport;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends ParentFragment<ProjectPresenter> implements ProjectContract.View, TabLayout.OnTabSelectedListener, OnRefreshListener, OnLoadMoreListener, ItemClickSupport.OnItemClickListener {

    @BindView(R.id.id_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.id_smartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;

    private int mCurrentPage = 1;
    private ProjectClassify mCurrentProjectClassify;

    private List<Article> mData;
    private BaseQuickAdapter mAdapter;

    private int mPosition = -1;

    @Override
    public void showProjectClassify(List<ProjectClassify> projectClassifyList) {
        for (ProjectClassify project : projectClassifyList) {
            mTabLayout.addTab(mTabLayout.newTab().setText(project.getName()).setTag(project));
        }
        mTabLayout.addOnTabSelectedListener(this);
        mCurrentProjectClassify = projectClassifyList.get(0);
        mPresenter.getProjectList(mCurrentPage, mCurrentProjectClassify.getId());
        showLoading();
    }

    @Override
    public void showProjectList(List<Article> articleList) {
        if (mCurrentPage == 1) {
            mAdapter.replaceData(articleList);
            mRefreshLayout.finishRefresh();
            // 处理reload的情况
            mRefreshLayout.finishLoadMore();
        } else {
            if (articleList.size() > 0) {
                mAdapter.addData(articleList);
            } else {
                CommonUtils.showSnackMessage(_mActivity, getString(R.string.no_more));
            }
            mRefreshLayout.finishLoadMore();
        }
        showNormal();
    }

    @Override
    public void refreshCollectState(CollectEvent event) {
        if (mPosition == -1 || mPosition >= mData.size()) return;
        Article article = mData.get(mPosition);
        boolean collect = article.isCollect();
        if (collect == event.isCollect()) return;
        article.setCollect(event.isCollect());
        mAdapter.setData(mPosition, article);
    }

    @Override
    protected int resource() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initDataAndEvent() {
        mPresenter.getProjectClassify();

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mData = new ArrayList<>();
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Object tag = tab.getTag();
        if (tag instanceof ProjectClassify) {
            ProjectClassify project = (ProjectClassify) tag;
            mPresenter.getProjectList(mCurrentPage = 1, project.getId());
            mCurrentProjectClassify = project;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mPresenter.getProjectList(mCurrentPage, mCurrentProjectClassify.getId());
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.getProjectList(mCurrentPage, mCurrentProjectClassify.getId());
        // mRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getProjectList(mCurrentPage, mCurrentProjectClassify.getId());
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
        Article article = mData.get(mPosition = position);
        intent.putExtra(Key.ARTICLE_LINK, article.getLink());
        intent.putExtra(Key.ARTICLE_TITLE, article.getTitle());
        intent.putExtra(Key.ARTICLE_ID, article.getId());
        intent.putExtra(Key.ARTICLE_IS_FAVORITE, article.isCollect());
        startActivity(intent);
    }

}

package com.jthou.wanandroid.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.ProjectContract;
import com.jthou.wanandroid.di.component.DaggerFragmentComponent;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.ProjectClassify;
import com.jthou.wanandroid.presenter.main.ProjectPresenter;
import com.jthou.wanandroid.ui.main.adapter.ProjectListAdapter;
import com.jthou.wanandroid.util.LogHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends ParentFragment<ProjectPresenter> implements ProjectContract.View, TabLayout.OnTabSelectedListener, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.id_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.id_smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;

    private int mCurrentPage = 1;
    private ProjectClassify mCurrentProjectClassify;

    private List<Article> mData;
    private BaseQuickAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DaggerFragmentComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);
        super.onCreate(savedInstanceState);
    }

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
            mSmartRefreshLayout.finishRefresh();
        } else {
            mAdapter.addData(articleList);
            mSmartRefreshLayout.finishLoadMore();
        }
        showNormal();

        LogHelper.e("showProjectList");
    }

    @Override
    protected int resource() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initDataAndEvent() {
        mPresenter.getProjectClassify();

        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        mData = new ArrayList<>();
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
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
        // mSmartRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getProjectList(mCurrentPage, mCurrentProjectClassify.getId());

        LogHelper.e("reload");
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

}

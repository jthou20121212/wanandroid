package com.jthou.wanandroid.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.KnowledgeHierarchyContract;
import com.jthou.wanandroid.di.component.DaggerFragmentComponent;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.presenter.main.KnowledgeHierarchyPresenter;
import com.jthou.wanandroid.ui.main.adapter.KnowledgeHierarchyAdapter;
import com.jthou.wanandroid.util.LogHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeHierarchyFragment extends ParentFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View, OnRefreshListener {

    @BindView(R.id.knowledge_hierarchy_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    private BaseQuickAdapter mAdapter;
    private List<KnowledgeHierarchy> mData;

    public KnowledgeHierarchyFragment() {}

    public static KnowledgeHierarchyFragment newInstance() {
        return new KnowledgeHierarchyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DaggerFragmentComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int resource() {
        return R.layout.fragment_knowledge_hierarchy;
    }

    @Override
    protected void initDataAndEvent() {
        mData = new ArrayList<>();
        mAdapter = new KnowledgeHierarchyAdapter(R.layout.item_knowledge_hierarchy, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mRefreshLayout.setOnRefreshListener(this);
        mPresenter.getKnowledgeHierarchyList();

        showLoading();
    }

    @Override
    public void showKnowledgeHierarchyList(List<KnowledgeHierarchy> knowledgeHierarchyList) {
        showNormal();
        mRefreshLayout.finishRefresh();

        // mData.addAll(knowledgeHierarchyList);
        // mAdapter.addData(knowledgeHierarchyList);
        mData.addAll(knowledgeHierarchyList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getKnowledgeHierarchyList();
    }

}

package com.jthou.wanandroid.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Constants;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.main.KnowledgeHierarchyDetailContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.presenter.main.KnowledgeHierarchyDetailPresenter;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.jthou.wanandroid.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeHierarchyDetailActivity extends BaseActivity<KnowledgeHierarchyDetailPresenter> implements
        KnowledgeHierarchyDetailContract.View, TabLayout.OnTabSelectedListener, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.id_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.id_smartRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int mCurrentPage;
    private int mCurrentCid;

    private List<Article> mData;
    private ArticleAdapter mAdapter;

    private int mPosition = -1;

    @Override
    protected int resource() {
        return R.layout.activity_knowledge_hierarchy_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KnowledgeHierarchy knowledgeHierarchy = getIntent().getParcelableExtra(Constants.IT_KNOWLEDGE_HIERARCHY);
        mToolbar.setTitle(knowledgeHierarchy.getName());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimary), 1f);
        mToolbar.setNavigationOnClickListener(this);

        mTabLayout.addOnTabSelectedListener(this);
        List<KnowledgeHierarchy> children = knowledgeHierarchy.getChildren();
        for (KnowledgeHierarchy k : children) {
            mTabLayout.addTab(mTabLayout.newTab().setText(k.getName()).setTag(k));
        }
        mTabLayout.getTabAt(0).select();

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mData = new ArrayList<>();
        mAdapter = new ArticleAdapter(R.layout.item_article, mData);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        KnowledgeHierarchy k = (KnowledgeHierarchy) tab.getTag();
        mPresenter.getArticleList(mCurrentPage = 0, mCurrentCid = k.getId());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.getArticleList(mCurrentPage, mCurrentCid);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.getArticleList(mCurrentPage, mCurrentCid);
        mRefreshLayout.finishLoadMore(2000);
    }

    @Override
    public void showArticleList(List<Article> articleList) {
        showNormal();
        mRefreshLayout.finishRefresh();
        if (mCurrentPage == 0)
            mAdapter.replaceData(articleList);
        else
            mAdapter.addData(articleList);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
        onBackPressedSupport();
    }

}

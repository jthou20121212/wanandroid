package com.jthou.wanandroid.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.di.component.DaggerFragmentComponent;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.presenter.main.HomePagerPresenter;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.jthou.wanandroid.util.AutoPlayViewPager;
import com.jthou.wanandroid.util.image.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomePageFragment extends ParentFragment<HomePagerPresenter> implements HomePageContract.View, OnRefreshListener, OnLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;

    private TextView mTvTitle;
    private TextView mTvIndex;
    private List<Banner> mBannerData;
    private AutoPlayViewPager mViewPager;

    private List<Article> mData;
    private ArticleAdapter mAdapter;

    private int mCurrentPage;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mViewPager != null)
            mViewPager.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mViewPager != null)
            mViewPager.stopAutoPlay();
    }

    @Override
    protected int resource() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initDataAndEvent() {
        // 初始化ViewPager
        View viewGroup = View.inflate(_mActivity, R.layout.banner_view, null);
        mTvTitle = viewGroup.findViewById(R.id.id_tv_title);
        mTvIndex = viewGroup.findViewById(R.id.id_tv_index);
        mViewPager = viewGroup.findViewById(R.id.id_viewPager);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(!isAdded()) return;
                if (mBannerData == null || mBannerData.isEmpty()) return;
                int index = position % mBannerData.size();
                mTvTitle.setText(mBannerData.get(index).getTitle());
                mTvIndex.setText(getString(R.string.index_count, index, mBannerData.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnItemClickListener(position -> {
            if (mBannerData == null || mBannerData.isEmpty()) return;
            int index = position % mBannerData.size();
            Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
            intent.putExtra(Key.ARTICLE_LINK, mBannerData.get(index).getUrl());
            intent.putExtra(Key.ARTICLE_TITLE, mBannerData.get(index).getTitle());
            startActivity(intent);
        });

        mData = new ArrayList<>();
        mAdapter = new ArticleAdapter(R.layout.item_article, mData);
        mAdapter.addHeaderView(viewGroup);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

        mPresenter.getArticleList(0);
        mPresenter.getBannerData();

        showLoading();
    }

    @Override
    public void showArticleList(List<Article> data) {
        showNormal();

        mRefreshLayout.finishLoadMore();
        // int size = mData.size();
        mData.addAll(data);
        // mAdapter.notifyItemRangeInserted(size, data.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBannerData(List<Banner> data) {
        mRefreshLayout.finishRefresh();
        if (data == null || data.isEmpty()) return;
        mBannerData = data;
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new AppCompatImageView(_mActivity);
                ImageLoader.displayImage(_mActivity, data.get(position).getImagePath(), imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

        };
        mViewPager.setAdapter(adapter);
        mViewPager.startAutoPlay();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleList(mCurrentPage = 0);
        mPresenter.getBannerData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleList(mCurrentPage++);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
        intent.putExtra(Key.ARTICLE_LINK, mData.get(position).getLink());
        intent.putExtra(Key.ARTICLE_TITLE, mData.get(position).getTitle());
        startActivity(intent);
    }

}

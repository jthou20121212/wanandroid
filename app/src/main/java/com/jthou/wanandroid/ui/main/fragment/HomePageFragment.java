package com.jthou.wanandroid.ui.main.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.presenter.main.HomePagerPresenter;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.adapter.ArticleAdapter;
import com.jthou.wanandroid.util.AutoPlayViewPager;
import com.jthou.wanandroid.util.CommonUtils;
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

    private List<Banner> mBannerData;
    private AutoPlayViewPager mViewPager;

    private List<Article> mData;
    private ArticleAdapter mAdapter;
    private ViewPager.OnPageChangeListener mListener;

    private int mCurrentPage;

    private int mPosition = -1;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewPager != null)
            mViewPager.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mViewPager != null)
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
        TextView tvTitle = viewGroup.findViewById(R.id.id_tv_title);
        TextView tvIndex = viewGroup.findViewById(R.id.id_tv_index);
        mViewPager = viewGroup.findViewById(R.id.id_viewPager);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(mListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!isAdded()) return;
                if (mBannerData == null) return;
                if (mBannerData.isEmpty()) return;
                int index = position % mBannerData.size();
                tvTitle.setText(mBannerData.get(index).getTitle());
                tvIndex.setText(getString(R.string.index_count, index + 1, mBannerData.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnItemClickListener(position -> {
            if (mBannerData == null) return;
            if (mBannerData.isEmpty()) return;
            int index = position % mBannerData.size();
            Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
            intent.putExtra(Key.IT_IS_BANNER, true);
            intent.putExtra(Key.ARTICLE_ID, mBannerData.get(index).getId());
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

        if (mCurrentPage == 0) {
            mAdapter.replaceData(data);
            mRefreshLayout.finishRefresh();
            // 处理reload的情况
            mRefreshLayout.finishLoadMore();
        } else {
            if (data.size() > 0) {
                mAdapter.addData(data);
            } else {
                CommonUtils.showSnackMessage(_mActivity, getString(R.string.no_more));
            }
            mRefreshLayout.finishLoadMore();
        }
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
        mViewPager.post(() -> mListener.onPageSelected(0));
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
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleList(mCurrentPage = 0);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getArticleList(++mCurrentPage);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(_mActivity, ArticleDetailActivity.class);
        Article article = mData.get(mPosition = position);
        intent.putExtra(Key.ARTICLE_LINK, article.getLink());
        intent.putExtra(Key.ARTICLE_TITLE, article.getTitle());
        intent.putExtra(Key.ARTICLE_ID, article.getId());
        intent.putExtra(Key.ARTICLE_IS_FAVORITE, article.isCollect());
        startActivity(intent);
    }

}

package com.jthou.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.main.ArticleDetailContract;
import com.jthou.wanandroid.di.component.DaggerActivityComponent;
import com.jthou.wanandroid.di.component.DaggerFragmentComponent;
import com.jthou.wanandroid.presenter.main.ArticleDetailPresenter;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View, View.OnClickListener {

    @BindView(R.id.article_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;

    private AgentWeb mAgentWeb;

    @Override
    protected int resource() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);

        String url = getIntent().getStringExtra(Key.ARTICLE_LINK);
        String title = getIntent().getStringExtra(Key.ARTICLE_TITLE);

        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(this);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.error_view, -1)
                .createAgentWeb().ready().go(url);
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setBlockNetworkImage(mPresenter.getNoImage());
        boolean autoCache = mPresenter.getAutoCache();
        settings.setAppCacheEnabled(autoCache);
        settings.setDomStorageEnabled(autoCache);
        settings.setDatabaseEnabled(autoCache);
        if (autoCache) {
            if (CommonUtils.isNetworkConnected()) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        // 不显示缩放按钮
        settings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

}

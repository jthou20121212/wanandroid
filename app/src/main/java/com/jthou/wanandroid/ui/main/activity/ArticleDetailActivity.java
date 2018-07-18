package com.jthou.wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.main.ArticleDetailContract;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.network.AbstractResponse;
import com.jthou.wanandroid.presenter.main.ArticleDetailPresenter;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import java.lang.reflect.Method;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View, View.OnClickListener {

    @BindView(R.id.article_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;

    private AgentWeb mAgentWeb;

    private MenuItem mFavoriteMenu;

    private int mArticleId;
    private boolean isFavorite;

    @Override
    protected int resource() {
        return R.layout.activity_article_detail;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArticleId = getIntent().getIntExtra(Key.ARTICLE_ID, 0);
        isFavorite = getIntent().getBooleanExtra(Key.ARTICLE_IS_FAVORITE, false);
        String url = getIntent().getStringExtra(Key.ARTICLE_LINK);
        String title = getIntent().getStringExtra(Key.ARTICLE_TITLE);

        mToolbar.setTitle(Html.fromHtml(title));
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
        } else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        // 不显示缩放按钮
        settings.setDisplayZoomControls(false);
        // 设置自适应屏幕，两者合用
        // 将图片调整到适合WebView的大小
        settings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_detail_menu, menu);
        mFavoriteMenu = menu.findItem(R.id.id_menu_favorite);
        boolean isFavorite = getIntent().getBooleanExtra(Key.ARTICLE_IS_FAVORITE, false);
        if (isFavorite)
            mFavoriteMenu.setIcon(R.drawable.icon_favorite);
        else
            mFavoriteMenu.setIcon(R.drawable.ic_toolbar_like_n);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.id_menu_favorite:
                if (isFavorite) {
                    mPresenter.cancelFavoriteArticle(mArticleId);
                } else {
                    mPresenter.favoriteArticle(mArticleId);
                }
                break;
            case R.id.id_menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                String url = getIntent().getStringExtra(Key.ARTICLE_LINK);
                String title = getIntent().getStringExtra(Key.ARTICLE_TITLE);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, getString(R.string.app_name), title, url));
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.id_menu_browser:
                String articleLink = getIntent().getStringExtra(Key.ARTICLE_LINK);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
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
        onBackPressedSupport();
    }

    @Override
    public void favoriteResult(AbstractResponse<String> response) {
        isFavorite = true;
        mFavoriteMenu.setIcon(R.drawable.icon_favorite);
        CommonUtils.showSnackMessage(this, getString(R.string.collect_success));
    }

    @Override
    public void cancelFavoriteResult() {
        isFavorite = false;
        mFavoriteMenu.setIcon(R.drawable.ic_toolbar_like_n);
        CommonUtils.showSnackMessage(this, getString(R.string.cancel_collect_success));
    }

    @Override
    public void onBackPressedSupport() {
        RxBus.getDefault().post(new CollectEvent(isFavorite));
        super.onBackPressedSupport();
    }

}

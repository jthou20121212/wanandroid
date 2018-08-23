package com.jthou.wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Constants;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.base.fragment.AbstractFragment;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.model.network.CookiesManager;
import com.jthou.wanandroid.presenter.main.MainPresenter;
import com.jthou.wanandroid.ui.login.LoginActivity;
import com.jthou.wanandroid.ui.main.fragment.FavoriteFragment;
import com.jthou.wanandroid.ui.main.fragment.HomePageFragment;
import com.jthou.wanandroid.ui.main.fragment.KnowledgeHierarchyFragment;
import com.jthou.wanandroid.ui.main.fragment.NavigationFragment;
import com.jthou.wanandroid.ui.main.fragment.ProjectFragment;
import com.jthou.wanandroid.ui.main.fragment.SearchFragment;
import com.jthou.wanandroid.ui.main.fragment.SettingFragment;
import com.jthou.wanandroid.util.BottomNavigationViewHelper;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.LogHelper;
import com.jthou.wanandroid.util.StatusBarUtil;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View,
        MenuItem.OnMenuItemClickListener, BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.id_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.id_bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private ActionBarDrawerToggle mDrawerListener;

    private HomePageFragment mHomePageFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private FavoriteFragment mFavoriteFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private SearchFragment mSearchFragment;
    private SettingFragment mSettingFragment;
    private AboutFragment mAboutFragment;

    private AbstractFragment mCurrentFragment;

    private int mShowFragment = Constants.TYPE_MAIN_PAGER;

    private MenuItem.OnMenuItemClickListener mListener;

    @Override
    protected int resource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimary), 1f);

        mDrawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                MenuItem item = mNavigationView.getMenu().findItem(R.id.id_menu_login);
                boolean loginState = mPresenter.getLoginState();
                if (!loginState) item.setChecked(false);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerListener);
        mDrawerListener.syncState();

        mNavigationView.getMenu().findItem(R.id.id_menu_login).setOnMenuItemClickListener(mListener = this);
        mNavigationView.getMenu().findItem(R.id.id_menu_about).setOnMenuItemClickListener(this);
        mNavigationView.getMenu().findItem(R.id.id_menu_setting).setOnMenuItemClickListener(this);
        mNavigationView.getMenu().findItem(R.id.id_menu_favorite).setOnMenuItemClickListener(this);
        mNavigationView.getMenu().findItem(R.id.id_menu_logout).setOnMenuItemClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        @SuppressLint("ResourceType")
        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.drawable.selector_drawer_item_text_color);
        mNavigationView.setItemIconTintList(colorStateList);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mCurrentFragment = mHomePageFragment = HomePageFragment.newInstance();
        mKnowledgeHierarchyFragment = KnowledgeHierarchyFragment.newInstance();
        mNavigationFragment = NavigationFragment.newInstance();
        mFavoriteFragment = FavoriteFragment.newInstance();
        mProjectFragment = ProjectFragment.newInstance();
        mSettingFragment = SettingFragment.newInstance();
        mAboutFragment = AboutFragment.newInstance();

        if (savedInstanceState == null) {
            mPresenter.setNightModeState(false);
        } else {
            mShowFragment = mPresenter.getCurrentItem();
            mCurrentFragment = getTargetFragment(mShowFragment);
            if (mShowFragment > Constants.TYPE_PROJECT)
                mBottomNavigationView.setVisibility(View.INVISIBLE);
        }

        mPresenter.autoLogin();

        mDelegate.loadMultipleRootFragment(R.id.id_content, mShowFragment, mHomePageFragment, mKnowledgeHierarchyFragment, mNavigationFragment, mProjectFragment, mFavoriteFragment, mSettingFragment, mAboutFragment);

        // 参数1：isManual 用户手动点击检查，非用户点击操作请传false
        // 参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
        Beta.checkUpgrade(false, false);
    }

    @Override
    protected void onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerListener);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_load_patch:
                 String test = null;
                 Log.e("jthou", "test : " + test.length());
                Log.e("jthou", "加载补丁包");
                LogHelper.e("加载补丁包");
                break;
            case R.id.id_load_info:
                LogHelper.e("补丁包信息");
                break;
            case R.id.id_search:
                if (mSearchFragment == null)
                    mSearchFragment = SearchFragment.newInstance();
                mSearchFragment.show(getSupportFragmentManager(), SearchFragment.class.getName());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        final int id = item.getItemId();
        switch (id) {
            case R.id.tab_home_page:
                // mDelegate.showHideFragment(mHomePageFragment, mCurrentFragment);
                mDelegate.showHideFragment(mHomePageFragment);
                mCurrentFragment = mHomePageFragment;
                mPresenter.setCurrentItem(Constants.TYPE_MAIN_PAGER);
                break;
            case R.id.tab_knowledge_hierarchy:
                mDelegate.showHideFragment(mKnowledgeHierarchyFragment, mCurrentFragment);
                mCurrentFragment = mKnowledgeHierarchyFragment;
                mPresenter.setCurrentItem(Constants.TYPE_KNOWLEDGE);
                break;
            case R.id.tab_navigation:
                mDelegate.showHideFragment(mNavigationFragment, mCurrentFragment);
                mCurrentFragment = mNavigationFragment;
                mPresenter.setCurrentItem(Constants.TYPE_NAVIGATION);
                break;
            case R.id.tab_project:
                mDelegate.showHideFragment(mProjectFragment, mCurrentFragment);
                mCurrentFragment = mProjectFragment;
                mPresenter.setCurrentItem(Constants.TYPE_PROJECT);
                break;
            default:
        }
        return false;
    }

    @Override
    public void onBackPressedSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (mCurrentFragment != mHomePageFragment) {
            mBottomNavigationView.setVisibility(View.VISIBLE);
            mBottomNavigationView.setSelectedItemId(R.id.tab_home_page);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.id_menu_login:
                if (mPresenter.getLoginState()) {
                    mDrawerLayout.closeDrawers();
                    mBottomNavigationView.setVisibility(View.VISIBLE);
                    mBottomNavigationView.setSelectedItemId(R.id.tab_home_page);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.id_menu_setting:
                mDelegate.showHideFragment(mSettingFragment, mCurrentFragment);
                mCurrentFragment = mSettingFragment;
                mPresenter.setCurrentItem(Constants.TYPE_SETTING);
                mDrawerLayout.closeDrawers();
                mBottomNavigationView.setVisibility(View.INVISIBLE);
                break;
            case R.id.id_menu_favorite:
                if (mPresenter.getLoginState()) {
                    mDelegate.showHideFragment(mFavoriteFragment, mCurrentFragment);
                    mCurrentFragment = mFavoriteFragment;
                    mPresenter.setCurrentItem(Constants.TYPE_COLLECT);
                    mDrawerLayout.closeDrawers();
                    mBottomNavigationView.setVisibility(View.INVISIBLE);
                } else {
                    Intent it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }
                break;
            case R.id.id_menu_about:
                mDelegate.showHideFragment(mAboutFragment, mCurrentFragment);
                mCurrentFragment = mAboutFragment;
                mPresenter.setCurrentItem(Constants.TYPE_ABOUT);
                mDrawerLayout.closeDrawers();
                mBottomNavigationView.setVisibility(View.INVISIBLE);
                break;
            case R.id.id_menu_logout:
                showLogoutDialog();
                break;
            default:
        }
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked()) {
                menu.getItem(i).setChecked(false);
            }
        }
        item.setChecked(true);
        return false;
    }

    @Override
    public void showUsername(String username, boolean isAutoLogin) {
        if (TextUtils.isEmpty(username)) return;
        mNavigationView.getMenu().findItem(R.id.id_menu_login).setTitle(username);
        String msg = isAutoLogin ? getString(R.string.auto_login_success) : getString(R.string.login_success);
        CommonUtils.showSnackMessage(this, msg);
    }

    @Override
    public void showLoginView() {
        MenuItem item = mNavigationView.getMenu().findItem(R.id.id_menu_logout);
        item.setVisible(true);
    }

    @Override
    public void switchNightMode(boolean nightMode) {
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showLogoutView() {
        MenuItem item = mNavigationView.getMenu().findItem(R.id.id_menu_logout);
        item.setVisible(false);
        mNavigationView.getMenu().findItem(R.id.id_menu_login).setTitle(R.string.login);
    }

    private AbstractFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_MAIN_PAGER:
                return mHomePageFragment;
            case Constants.TYPE_KNOWLEDGE:
                return mKnowledgeHierarchyFragment;
            case Constants.TYPE_NAVIGATION:
                return mNavigationFragment;
            case Constants.TYPE_PROJECT:
                return mProjectFragment;
            case Constants.TYPE_COLLECT:
                return mFavoriteFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_ABOUT:
                return mAboutFragment;
        }
        return mHomePageFragment;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prompt);
        builder.setMessage(R.string.prompt_message);
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            mPresenter.logout();
            CookiesManager.clearAllCookies();
        });
        builder.show();

        CommonUtils.showSnackMessage(this, "退出不了嘞");
    }

    public void toHomePage() {
        MenuItem item = mNavigationView.getMenu().findItem(R.id.id_menu_login);
        mListener.onMenuItemClick(item);
    }

}

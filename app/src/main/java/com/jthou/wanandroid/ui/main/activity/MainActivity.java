package com.jthou.wanandroid.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Constant;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.base.fragment.BaseFragment;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.di.component.DaggerActivityComponent;
import com.jthou.wanandroid.presenter.main.MainPresenter;
import com.jthou.wanandroid.ui.login.LoginActivity;
import com.jthou.wanandroid.ui.main.fragment.FavoriteFragment;
import com.jthou.wanandroid.ui.main.fragment.HomePageFragment;
import com.jthou.wanandroid.ui.main.fragment.KnowledgeHierarchyFragment;
import com.jthou.wanandroid.ui.main.fragment.NavigationFragment;
import com.jthou.wanandroid.ui.main.fragment.ProjectFragment;
import com.jthou.wanandroid.ui.main.fragment.SearchFragment;
import com.jthou.wanandroid.util.BottomNavigationViewHelper;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.L;
import com.jthou.wanandroid.util.LogHelper;
import com.jthou.wanandroid.util.StatusBarUtil;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener, MenuItem.OnMenuItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.id_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.id_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.id_bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;

    private HomePageFragment mHomePageFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private FavoriteFragment mFavoriteFragment;
    private NavigationFragment mNavigationFragment;
    private ProjectFragment mProjectFragment;
    private SearchFragment mSearchFragment;

    private ISupportFragment mCurrentFragment;

    private int mHideFragment = Constant.TYPE_MAIN_PAGER;
    private int mShowFragment = Constant.TYPE_MAIN_PAGER;

    @Override
    protected int resource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerActivityComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);
        super.onCreate(savedInstanceState);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.colorPrimary), 1f);

        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        mNavigationView.getMenu().findItem(R.id.id_menu_login).setOnMenuItemClickListener(this);
        mNavigationView.getMenu().findItem(R.id.id_menu_about).setOnMenuItemClickListener(this);
        mNavigationView.getMenu().findItem(R.id.id_menu_favorite).setOnMenuItemClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

        mCurrentFragment = mHomePageFragment = HomePageFragment.newInstance();
        mKnowledgeHierarchyFragment = KnowledgeHierarchyFragment.newInstance();
        mNavigationFragment = NavigationFragment.newInstance();
        mFavoriteFragment = FavoriteFragment.newInstance();
        mProjectFragment = ProjectFragment.newInstance();

        if (savedInstanceState == null)
            mPresenter.autoLogin();
        else
            mShowFragment = mPresenter.getCurrentItem();

        mDelegate.loadMultipleRootFragment(R.id.id_content, mShowFragment, mHomePageFragment, mKnowledgeHierarchyFragment, mNavigationFragment, mFavoriteFragment, mProjectFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_search:
                if(mSearchFragment == null)
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
                mDelegate.showHideFragment(mHomePageFragment, mCurrentFragment);
                mCurrentFragment = mHomePageFragment;
                break;
            case R.id.tab_knowledge_hierarchy:
                mDelegate.showHideFragment(mKnowledgeHierarchyFragment, mCurrentFragment);
                mCurrentFragment = mKnowledgeHierarchyFragment;
                break;
            case R.id.tab_navigation:
                mDelegate.showHideFragment(mNavigationFragment, mCurrentFragment);
                mCurrentFragment = mNavigationFragment;
                break;
            case R.id.tab_project:
                mDelegate.showHideFragment(mProjectFragment, mCurrentFragment);
                mCurrentFragment = mProjectFragment;
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
        super.onBackPressedSupport();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.id_menu_login:
                if (mPresenter.getLoginState()) {
                    mDelegate.showHideFragment(mHomePageFragment, mCurrentFragment);
                    mCurrentFragment = mHomePageFragment;
                    mDrawerLayout.closeDrawers();
                    mBottomNavigationView.setVisibility(View.VISIBLE);
                    mBottomNavigationView.setSelectedItemId(R.id.tab_home_page);
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.id_menu_about:
                break;
            case R.id.id_menu_favorite:
                if (mPresenter.getLoginState()) {
                    mDelegate.showHideFragment(mFavoriteFragment, mCurrentFragment);
                    mCurrentFragment = mFavoriteFragment;
                    mDrawerLayout.closeDrawers();
                    mBottomNavigationView.setVisibility(View.GONE);
                } else {
                    Intent it = new Intent(this, LoginActivity.class);
                    startActivity(it);
                }
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
    public void showUsername(String username) {
        if (TextUtils.isEmpty(username)) return;
        mNavigationView.getMenu().findItem(R.id.id_menu_login).setTitle(username);
        // mNavigationView.getMenu().findItem(R.id.id_menu_login).setOnMenuItemClickListener(null);
    }

    @Override
    public void showAutoLogin() {
        CommonUtils.showSnackMessage(this, getString(R.string.auto_login_success));
    }

}

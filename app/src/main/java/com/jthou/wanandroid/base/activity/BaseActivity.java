package com.jthou.wanandroid.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.util.ActivityContainer;
import com.jthou.wanandroid.util.AntiShakeUtils;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.L;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity<T extends BasePresenter> extends AbstractActivity implements
        BaseView, HasSupportFragmentInjector {

    private Unbinder mUnbinder;

    @Inject
    protected T mPresenter;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        L.e("Activity : " + getClass().getName());

        ActivityContainer.getInstance().addActivity(this);

        if (resource() != 0) {
            setContentView(resource());
            mUnbinder = ButterKnife.bind(this);
        }

        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) mUnbinder.unbind();
        if (mPresenter != null) mPresenter.detachView(this);
        ActivityContainer.getInstance().removeActivity(this);
        AntiShakeUtils.getInstance().remove(getClass().getName());
        super.onDestroy();
    }

    protected abstract int resource();

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showErrorMsg(String message) {
        CommonUtils.showSnackMessage(this, message);
    }

    @Override
    public boolean isShow() {
        return true;
    }

}

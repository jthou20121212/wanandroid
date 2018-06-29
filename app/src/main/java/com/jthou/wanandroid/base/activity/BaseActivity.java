package com.jthou.wanandroid.base.activity;

import android.os.Bundle;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.util.ActivityContainer;
import com.jthou.wanandroid.util.AntiShakeUtils;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.L;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AbstractActivity implements BaseView {

    private Unbinder mUnbinder;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContainer.getInstance().addActivity(this);
        L.e("Activity : " + getClass().getName());

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

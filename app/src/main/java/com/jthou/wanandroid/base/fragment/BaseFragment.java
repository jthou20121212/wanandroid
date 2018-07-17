package com.jthou.wanandroid.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<T extends BasePresenter> extends AbstractFragment implements BaseView {

    private Unbinder mUnbinder;

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Glide.with(this).clear(imageView);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resource(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        if (mPresenter != null)
            mPresenter.attachView(this);
        return view;
    }

    protected abstract int resource();

    @Override
    public void onDestroyView() {
        if (mPresenter != null)
            mPresenter.detachView(this);
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

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
    public boolean isShow() {
        return isAdded();
    }

}

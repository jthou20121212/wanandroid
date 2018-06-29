package com.jthou.wanandroid.base;

/**
 * Created by user on 2018/5/16.
 */

public interface BaseView
{
    void useNightMode(boolean isNightMode);

    void showErrorMsg(String errorMsg);

    void showNormal();

    void showError();

    void showLoading();

    void reload();

    void showLoginView();

    void showLogoutView();

    boolean isShow();

}

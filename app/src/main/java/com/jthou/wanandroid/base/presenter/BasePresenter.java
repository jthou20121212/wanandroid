package com.jthou.wanandroid.base.presenter;

/**
 * Created by user on 2018/5/16.
 */

public interface BasePresenter<T> {

    void attachView(T view);

    void detachView(T view);

}

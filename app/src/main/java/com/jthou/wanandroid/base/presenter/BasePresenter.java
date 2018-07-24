package com.jthou.wanandroid.base.presenter;


public interface BasePresenter<T> {

    void attachView(T view);

    void detachView(T view);

}

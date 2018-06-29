package com.jthou.wanandroid.base.presenter;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.model.DataManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by user on 2018/5/16.
 */

public class ParentPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    protected DataManager mDataManager;

    public ParentPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    protected void addSubscribe(Disposable disposable) {
        if(mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        if(mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView(T view) {
        this.mView = null;
        unSubscribe();
    }

}

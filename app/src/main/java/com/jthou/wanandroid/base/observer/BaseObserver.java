package com.jthou.wanandroid.base.observer;

import android.text.TextUtils;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.util.LogHelper;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * @param <T>
 * @author quchao
 * @date 2017/11/27
 */

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorMsg = true;
    private boolean isShowErrorView = true;

    protected BaseObserver(BaseView view) {
        this.mView = view;
    }

    protected BaseObserver(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(BaseView view, boolean isShowError) {
        this.mView = view;
        this.isShowErrorView = isShowError;
    }

    protected BaseObserver(BaseView view, String errorMsg, boolean isShowError) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorView = isShowError;
    }

    protected BaseObserver(BaseView view, String errorMsg, boolean showErrorMsg, boolean isShowErrorView) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorMsg = showErrorMsg;
        this.isShowErrorView = isShowErrorView;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null || !mView.isShow()) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            showErrorMsg(mErrorMsg);
        }
//        else if (e instanceof ServerException) {
//            mView.showErrorMsg(e.toString());
//        }
        else if (e instanceof HttpException) {
            showErrorMsg(WanAndroidApp.getInstance().getString(R.string.http_error));
        } else {
            showErrorMsg(WanAndroidApp.getInstance().getString(R.string.unKnown_error));
            LogHelper.d(e.toString());
        }
        if (isShowErrorView) {
            mView.showError();
        }
    }

    private void showErrorMsg(String errorMsg) {
        if(isShowErrorMsg)
            mView.showErrorMsg(errorMsg);
    }

}

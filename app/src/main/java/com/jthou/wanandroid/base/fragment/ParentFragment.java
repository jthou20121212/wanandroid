package com.jthou.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Constants;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.annotation.State;
import com.jthou.wanandroid.util.CommonUtils;

public abstract class ParentFragment<T extends BasePresenter> extends BaseFragment<T> {

    private View mErrorView;
    private View mNormalView;
    private View mLoadingView;

    private LottieAnimationView mLottieAnimationView;

    @State
    private int mCurrentState;

    public ParentFragment() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mNormalView = getView().findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("布局文件必须包含一个id为normal_view的view");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("mNormalView的父控件必须是ViewGroup");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(_mActivity, R.layout.loading_view, parent);
        View.inflate(_mActivity, R.layout.error_view, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mErrorView = parent.findViewById(R.id.error_group);
        TextView reloadTv = mErrorView.findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(v -> reload());
        mLottieAnimationView = mLoadingView.findViewById(R.id.loading_animation);
        mErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);

        initDataAndEvent();
    }

    protected abstract void initDataAndEvent();

    @Override
    public void onDestroyView() {
        if(mLottieAnimationView != null)
            mLottieAnimationView.cancelAnimation();
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        if(mCurrentState == Constants.LOADING_STATE) return;
        hideCurrentView(mCurrentState);
        mCurrentState = Constants.LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLottieAnimationView.setAnimation("loading_bus.json");
        mLottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        mLottieAnimationView.playAnimation();
    }

    @Override
    public void showError() {
        if (mCurrentState == Constants.ERROR_STATE) {
            return;
        }
        hideCurrentView(mCurrentState);
        mCurrentState = Constants.ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        if (mCurrentState == Constants.NORMAL_STATE) {
            return;
        }
        hideCurrentView(mCurrentState);
        mCurrentState = Constants.NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String message) {
        CommonUtils.showSnackMessage(_mActivity, message);
    }

    private void hideCurrentView(@State int state) {
        switch (state) {
            case Constants.NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case Constants.LOADING_STATE:
                mLottieAnimationView.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;
            case Constants.ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
            default:
                break;
        }
    }
    
}

package com.jthou.wanandroid.presenter.main;

import android.text.TextUtils;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.NightModeEvent;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

public class MainPresenter extends ParentPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    private void showLoginInfo(boolean isAutoLogin) {
        if (mView == null) return;
        String username = mDataManager.getUsername();
        mView.showLoginView();
        mView.showUsername(username, isAutoLogin);
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class).subscribe(loginEvent -> showLoginInfo(loginEvent.isAutoLogin())));

        addSubscribe(RxBus.getDefault().toFlowable(NightModeEvent.class).subscribe(nightModeEvent -> mView.switchNightMode(nightModeEvent.getNightMode())));
    }

    @Override
    public void autoLogin() {
        String username = mDataManager.getUsername();
        String password = mDataManager.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return;
        addSubscribe(mDataManager.login(username, password)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribeWith(new BaseObserver<LoginInfo>(mView, false) {
                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        LoginEvent loginEvent = new LoginEvent();
                        loginEvent.setAutoLogin(true);
                        RxBus.getDefault().post(loginEvent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDataManager.saveLoginState(false);
                        super.onError(e);
                    }
                }));
    }

    @Override
    public int getCurrentItem() {
        return mDataManager.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int currentItem) {
        mDataManager.saveCurrentItem(currentItem);
    }

    @Override
    public void setNightModeState(boolean nightModeState) {
        mDataManager.setNightMode(nightModeState);
    }

    @Override
    public void logout() {
        mDataManager.logout();
        if(mView != null && mView.isShow())
            mView.showLogoutView();
    }

    @Override
    public boolean getLoginState() {
        return mDataManager.getLoginState();
    }

}

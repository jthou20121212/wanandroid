package com.jthou.wanandroid.presenter.main;

import android.text.TextUtils;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

public class MainPresenter extends ParentPresenter<MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getUsername() {
        if (mView == null) return;
        String username = mDataManager.getUsername();
        mView.showUsername(username);
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class).subscribe(loginEvent -> getUsername()));
    }

    public void autoLogin() {
        String username = mDataManager.getUsername();
        String password = mDataManager.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return;
        addSubscribe(mDataManager.login(username, password)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribeWith(new BaseObserver<LoginInfo>(mView, null, false, false) {
                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        mView.showUsername(loginInfo.getUsername());
                        mView.showAutoLogin();
                    }
                }));
    }

    @Override
    public int getCurrentItem() {
        return mDataManager.getCurrentItem();
    }

    @Override
    public boolean getLoginState() {
        return mDataManager.getLoginState();
    }

}

package com.jthou.wanandroid.presenter.login;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.contract.login.LoginContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.functions.Predicate;

public class LoginPresenter extends ParentPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void login(String username, String password) {
        addSubscribe(mDataManager.login(username, password)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .filter(loginInfo -> mView != null)
                .subscribeWith(new BaseObserver<LoginInfo>(mView,
                        WanAndroidApp.getInstance().getString(R.string.login_fail)) {
                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        mDataManager.saveUsername(loginInfo.getUsername());
                        mDataManager.savePassword(loginInfo.getPassword());
                        mDataManager.saveLoginState(true);

                        LoginEvent loginEvent = new LoginEvent();
                        RxBus.getDefault().post(loginEvent);

                        mView.showLoginInfo(loginInfo);
                    }
                }));
    }


}

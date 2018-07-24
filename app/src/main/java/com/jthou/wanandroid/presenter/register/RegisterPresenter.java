package com.jthou.wanandroid.presenter.register;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.register.RegisterContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.RegisterResult;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.util.L;
import com.jthou.wanandroid.util.LogHelper;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.functions.Predicate;


public class RegisterPresenter extends ParentPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter(DataManager mDataManager) {
        super(mDataManager);
    }


    @Override
    public void register(String username, String password, String repassword) {
        addSubscribe(mDataManager.register(username, password, repassword)
                .compose(RxUtil.schedulerHelper())
                .filter(result -> mView != null && mView.isShow())
                .subscribeWith(new BaseObserver<RegisterResult>(mView, WanAndroidApp.getInstance().getString(R.string.register_fail)) {
                    @Override
                    public void onNext(RegisterResult result) {
                        if (result.getErrorCode() >= 0) {
                            LoginInfo loginInfo = result.getData();
                            mDataManager.saveUsername(loginInfo.getUsername());
                            mDataManager.savePassword(loginInfo.getPassword());
                            mDataManager.saveLoginState(true);

                            LoginEvent loginEvent = new LoginEvent();
                            RxBus.getDefault().post(loginEvent);

                            mView.handleRegisterResult();
                        } else {
                            mView.showErrorMsg(result.getErrorMsg());
                        }
                    }
                }));
    }

}

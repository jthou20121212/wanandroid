package com.jthou.wanandroid.contract.login;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.model.entity.LoginInfo;

/**
 * Created by user on 2018/5/22.
 */

public interface LoginContract {

    public interface View extends BaseView {

        void showLoginInfo(LoginInfo loginInfo);

    }

    public interface Presenter extends BasePresenter<View> {

        void login(String username, String password);

    }

}

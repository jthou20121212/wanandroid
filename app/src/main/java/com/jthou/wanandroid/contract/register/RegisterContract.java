package com.jthou.wanandroid.contract.register;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.RegisterResult;

/**
 * Created by user on 2018/7/24.
 */

public interface RegisterContract {

    public interface View extends BaseView {

        void handleRegisterResult();

    }

    public interface Presenter extends BasePresenter<View> {

        void register(String username, String password, String repassword);

    }

}

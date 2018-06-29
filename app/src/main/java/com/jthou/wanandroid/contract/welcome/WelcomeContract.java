package com.jthou.wanandroid.contract.welcome;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;

/**
 * Created by user on 2018/5/31.
 */

public interface WelcomeContract {

    interface View extends BaseView {

        void toMain();

    }

    interface Presenter extends BasePresenter<View> {

    }

}

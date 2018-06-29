package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;

import java.util.List;

/**
 * Created by user on 2018/5/17.
 */

public interface MainContract {

    interface View extends BaseView {

        void showUsername(String username);

        void showAutoLogin();
    }

    interface Presenter extends BasePresenter<View> {

        void getUsername();

        boolean getLoginState();

        void autoLogin();

        int getCurrentItem();

    }

}

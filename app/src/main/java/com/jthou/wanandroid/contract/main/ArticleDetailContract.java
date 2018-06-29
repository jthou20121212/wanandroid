package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;

/**
 * Created by user on 2018/5/22.
 */

public interface ArticleDetailContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

        boolean getAutoCache();

        boolean getNoImage();

    }

}

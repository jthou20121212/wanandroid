package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Navigation;

import java.util.List;

public interface NavigationContract {

    interface View extends BaseView {

        void showNavigationData(List<Navigation> data);

    }

    interface Presenter extends BasePresenter<View> {

        void getNavigationList();

    }

}

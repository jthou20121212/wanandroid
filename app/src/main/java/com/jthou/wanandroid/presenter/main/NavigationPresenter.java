package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.NavigationContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

public class NavigationPresenter extends ParentPresenter<NavigationContract.View> implements NavigationContract.Presenter {

    @Inject
    public NavigationPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getNavigationList() {
        addSubscribe(mDataManager.getNavigationList()
        .compose(RxUtil.schedulerHelper())
        .compose(RxUtil.handleResponse())
        .subscribeWith(new BaseObserver<List<Navigation>>(mView) {

            @Override
            public void onNext(List<Navigation> navigations) {
                mView.showNavigationData(navigations);
            }

        }));
    }

}

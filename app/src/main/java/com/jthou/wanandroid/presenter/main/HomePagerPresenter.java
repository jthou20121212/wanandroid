package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.entity.NightModeEvent;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.util.LogHelper;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

/**
 * Created by user on 2018/5/17.
 */

public class HomePagerPresenter extends ParentPresenter<HomePageContract.View> implements HomePageContract.Presenter {

    @Inject
    public HomePagerPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void attachView(HomePageContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class).subscribe(collectEvent -> mView.refreshCollectState(collectEvent)));
    }

    @Override
    public void getArticleList(int page) {
        addSubscribe(mDataManager.getKnowledgeHierarchyArticleList(page)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribe(articles -> mView.showArticleList(articles.getDatas())));
    }

    @Override
    public void getBannerData() {
        LogHelper.e("getBannerData");
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribe(banners -> mView.showBannerData(banners)));
    }

}

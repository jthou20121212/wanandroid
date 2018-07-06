package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.model.DataManager;
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
    public void getArticleList(int page) {
        addSubscribe(mDataManager.getKnowledgeHierarchyArticleList(page)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribe(articles -> mView.showArticleList(articles.getDatas())));
    }

    @Override
    public void getBannerData() {
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribe(banners -> mView.showBannerData(banners)));
    }

}

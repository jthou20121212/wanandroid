package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.network.BaseResponse;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

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
                .subscribeWith(new BaseObserver<BaseResponse<Article>>(mView) {
                    @Override
                    public void onNext(BaseResponse<Article> articles) {
                        mView.showArticleList(articles.getDatas());
                    }
                }));
    }

    @Override
    public void getBannerData() {
        addSubscribe(mDataManager.getBannerData()
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribeWith(new BaseObserver<List<Banner>>(mView) {
                    @Override
                    public void onNext(List<Banner> banners) {
                        mView.showBannerData(banners);
                    }
                }));
    }

}

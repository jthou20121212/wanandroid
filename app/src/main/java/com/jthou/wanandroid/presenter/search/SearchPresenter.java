package com.jthou.wanandroid.presenter.search;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.contract.search.SearchContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 2018/6/1.
 */

public class SearchPresenter extends ParentPresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void attachView(SearchContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class).subscribe(collectEvent -> mView.refreshCollectState(collectEvent)));
    }

    @Override
    public void getSearchList(int page, String keyword) {
        addSubscribe(mDataManager.getSearchList(page, keyword)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .map(baseData -> baseData.getDatas())
                .subscribeWith(new BaseObserver<List<Article>>(mView) {
                    @Override
                    public void onNext(List<Article> articleList) {
                        mView.showSearchResult(articleList);
                    }
                }));
    }

}

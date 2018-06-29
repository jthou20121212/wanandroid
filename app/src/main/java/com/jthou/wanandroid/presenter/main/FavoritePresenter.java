package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.FavoriteContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.network.BaseResponse;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

public class FavoritePresenter extends ParentPresenter<FavoriteContract.View> implements FavoriteContract.Presenter {

    @Inject
    public FavoritePresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getFavoriteArticleList(int page) {
        addSubscribe(mDataManager.getFavoriteArticleList(page)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribeWith(new BaseObserver<BaseResponse<Article>>(mView) {

                    @Override
                    public void onNext(BaseResponse<Article> articleBaseResponse) {
                        mView.showFavoriteArticleList(articleBaseResponse.getDatas());
                    }
                }));
    }

}

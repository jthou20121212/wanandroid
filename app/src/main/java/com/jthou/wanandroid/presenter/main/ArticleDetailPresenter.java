package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.ArticleDetailContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.network.AbstractResponse;
import com.jthou.wanandroid.util.RxUtil;

import javax.inject.Inject;

public class ArticleDetailPresenter extends ParentPresenter<ArticleDetailContract.View> implements ArticleDetailContract.Presenter {

    @Inject
    public ArticleDetailPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public boolean getAutoCache() {
        return mDataManager.autoCache();
    }

    @Override
    public boolean getNoImage() {
        return mDataManager.noImage();
    }

    @Override
    public void favoriteArticle(int articleId) {
        addSubscribe(mDataManager.collect(articleId)
                .compose(RxUtil.schedulerHelper())
                .subscribeWith(new BaseObserver<AbstractResponse<String>>(mView, WanAndroidApp.getInstance().getString(R.string.collect_failure)) {
                    @Override
                    public void onNext(AbstractResponse<String> result) {
                        if (result.getErrorCode() == 0)
                            mView.favoriteResult(result);
                    }
                }));
    }

    @Override
    public void cancelFavoriteArticle(int articleId) {
        addSubscribe(mDataManager.cancelCollect(articleId)
                .compose(RxUtil.schedulerHelper())
                .subscribeWith(new BaseObserver<AbstractResponse<String>>(mView, WanAndroidApp.getInstance().getString(R.string.cancel_collect_failure)) {
                    @Override
                    public void onNext(AbstractResponse<String> result) {
                        if (result.getErrorCode() == 0)
                            mView.cancelFavoriteResult();
                    }
                }));
    }

}

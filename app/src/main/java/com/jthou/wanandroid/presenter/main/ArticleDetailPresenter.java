package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.ArticleDetailContract;
import com.jthou.wanandroid.model.DataManager;

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

}

package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.model.network.AbstractResponse;

/**
 * Created by user on 2018/5/22.
 */

public interface ArticleDetailContract {

    public interface View extends BaseView {

        void favoriteResult(AbstractResponse<String> response);

        void cancelFavoriteResult();

    }

    public interface Presenter extends BasePresenter<View> {

        boolean getAutoCache();

        boolean getNoImage();

        void favoriteArticle(int articleId);

        void cancelFavoriteArticle(int articleId);

    }

}

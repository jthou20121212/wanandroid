package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;

import java.util.List;

/**
 * Created by user on 2018/5/24.
 */

public interface FavoriteContract {

    interface View extends BaseView {

        void showFavoriteArticleList(List<Article> data);

        void refreshCollectState(CollectEvent event);

    }

    interface Presenter extends BasePresenter<View> {

        void getFavoriteArticleList(int page);

    }

}

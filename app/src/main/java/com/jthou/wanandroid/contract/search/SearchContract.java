package com.jthou.wanandroid.contract.search;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;

import java.util.List;

/**
 * Created by user on 2018/6/1.
 */

public interface SearchContract {

    interface View extends BaseView {

        void showSearchResult(List<Article> articleList);

        void refreshCollectState(CollectEvent event);

    }


    interface Presenter extends BasePresenter<View> {

        void getSearchList(int page, String keyword);

    }

}

package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.CollectEvent;

import java.util.List;

/**
 * Created by user on 2018/5/17.
 */

public interface HomePageContract {

    interface View extends BaseView {

        void showArticleList(List<Article> data);

        void showBannerData(List<Banner> data);

        void refreshCollectState(CollectEvent event);

    }

    interface Presenter extends BasePresenter<View> {

        void getArticleList(int page);

        void getBannerData();

    }

}

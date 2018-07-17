package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;

import java.util.List;

public interface KnowledgeHierarchyDetailContract {

    interface View extends BaseView {

        void showArticleList(List<Article> articleList);

        void refreshCollectState(CollectEvent event);

    }

    interface Presenter extends BasePresenter<View> {

        void getArticleList(int page, int cid);

    }

}

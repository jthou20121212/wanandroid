package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.ProjectClassify;

import java.util.List;

/**
 * Created by user on 2018/5/29.
 */

public interface ProjectContract {

    interface View extends BaseView {

        void showProjectClassify(List<ProjectClassify> projectClassifyList);

        void showProjectList(List<Article> articleList);

    }

    interface Presenter extends BasePresenter<View> {

        void getProjectClassify();

        void getProjectList(int page, int cid);

    }

}

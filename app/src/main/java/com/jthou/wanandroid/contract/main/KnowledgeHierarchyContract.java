package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;

import java.util.List;

/**
 * Created by user on 2018/5/23.
 */

public interface KnowledgeHierarchyContract {

    interface View extends BaseView {

        void showKnowledgeHierarchyList(List<KnowledgeHierarchy> knowledgeHierarchyList);

    }

    interface Presenter extends BasePresenter<View> {

        void getKnowledgeHierarchyList();

    }

}

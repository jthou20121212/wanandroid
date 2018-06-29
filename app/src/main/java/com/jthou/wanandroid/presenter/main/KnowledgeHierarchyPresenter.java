package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.KnowledgeHierarchyContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

public class KnowledgeHierarchyPresenter extends ParentPresenter<KnowledgeHierarchyContract.View> implements KnowledgeHierarchyContract.Presenter {

    @Inject
    public KnowledgeHierarchyPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getKnowledgeHierarchyList() {
        addSubscribe(mDataManager.getKnowledgeHierarchyList()
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .filter(knowledgeHierarchyList -> mView != null)
                .subscribeWith(new BaseObserver<List<KnowledgeHierarchy>>(mView) {
                    @Override
                    public void onNext(List<KnowledgeHierarchy> knowledgeHierarchies) {
                        mView.showKnowledgeHierarchyList(knowledgeHierarchies);
                    }
                }));
    }

}

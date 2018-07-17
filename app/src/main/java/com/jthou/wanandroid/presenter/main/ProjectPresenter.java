package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.HomePageContract;
import com.jthou.wanandroid.contract.main.ProjectContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.entity.ProjectClassify;
import com.jthou.wanandroid.model.network.BaseResponse;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class ProjectPresenter extends ParentPresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Inject
    public ProjectPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void attachView(ProjectContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class).subscribe(collectEvent -> mView.refreshCollectState(collectEvent)));
    }


    @Override
    public void getProjectClassify() {
        addSubscribe(mDataManager.getProjectClassifyList()
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .subscribeWith(new BaseObserver<List<ProjectClassify>>(mView) {
                    @Override
                    public void onNext(List<ProjectClassify> projectClassifyList) {
                        mView.showProjectClassify(projectClassifyList);
                    }
                }));
    }

    @Override
    public void getProjectList(int page, int cid) {
        addSubscribe(mDataManager.getProjectListByClassify(page, cid)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .map(articleBaseResponse -> articleBaseResponse.getDatas())
                .subscribeWith(new BaseObserver<List<Article>>(mView) {
                    @Override
                    public void onNext(List<Article> articleList) {
                        mView.showProjectList(articleList);
                    }
                }));
    }

}

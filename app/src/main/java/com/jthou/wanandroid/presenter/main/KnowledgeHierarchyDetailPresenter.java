package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.KnowledgeHierarchyDetailContract;
import com.jthou.wanandroid.contract.main.MainContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.CollectEvent;
import com.jthou.wanandroid.model.network.BaseResponse;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;


public class KnowledgeHierarchyDetailPresenter extends ParentPresenter<KnowledgeHierarchyDetailContract.View> implements KnowledgeHierarchyDetailContract.Presenter {

    @Inject
    public KnowledgeHierarchyDetailPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void attachView(KnowledgeHierarchyDetailContract.View view) {
        super.attachView(view);

        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CollectEvent.class).subscribe(collectEvent -> mView.refreshCollectState(collectEvent)));
    }

    @Override
    public void getArticleList(int page, int cid) {
        addSubscribe(mDataManager.getKnowledgeHierarchyArticleList(page, cid)
                .compose(RxUtil.schedulerHelper())
                .compose(RxUtil.handleResponse())
                .map(new Function<BaseResponse<Article>, List<Article>>() {
                    @Override
                    public List<Article> apply(BaseResponse<Article> articleBaseResponse) throws Exception {
                        return articleBaseResponse.getDatas();
                    }
                })
                .subscribeWith(new BaseObserver<List<Article>>(mView) {
                    @Override
                    public void onNext(List<Article> articleList) {
                        mView.showArticleList(articleList);
                    }
                }));

    }

}

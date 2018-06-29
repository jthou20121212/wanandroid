package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.observer.BaseObserver;
import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.SearchContract;
import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 2018/5/30.
 */

public class SearchPresenter extends ParentPresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void getHotKeyList() {
         addSubscribe(mDataManager.getHotKeyList()
                 .compose(RxUtil.schedulerHelper())
                 .compose(RxUtil.handleResponse())
         .subscribeWith(new BaseObserver<List<HotKey>>(mView) {
             @Override
             public void onNext(List<HotKey> hotKeys) {
                 mView.showHotKeyList(hotKeys);
             }
         }));
    }

}

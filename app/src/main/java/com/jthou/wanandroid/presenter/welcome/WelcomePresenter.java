package com.jthou.wanandroid.presenter.welcome;

import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.welcome.WelcomeContract;
import com.jthou.wanandroid.model.DataManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class WelcomePresenter extends ParentPresenter<WelcomeContract.View> implements WelcomeContract.Presenter {

    @Inject
    public WelcomePresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public void attachView(WelcomeContract.View view) {
        super.attachView(view);
        addSubscribe(Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(aLong -> mView.toMain()));
    }

}

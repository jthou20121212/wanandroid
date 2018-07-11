package com.jthou.wanandroid.presenter.main;

import com.jthou.wanandroid.base.presenter.ParentPresenter;
import com.jthou.wanandroid.contract.main.SettingContract;
import com.jthou.wanandroid.model.DataManager;

import javax.inject.Inject;

public class SettingPresenter extends ParentPresenter<SettingContract.View> implements SettingContract.Presenter {

    @Inject
    public SettingPresenter(DataManager mDataManager) {
        super(mDataManager);
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.autoCache();
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.noImage();
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.nightMode();
    }

    @Override
    public void setNightModeState(boolean mode) {
        mDataManager.setNightMode(mode);
    }

    @Override
    public void checkVersion(String versionName) {

    }

    @Override
    public void setNoImageState(boolean b) {
        mDataManager.setNoImage(b);
    }

    @Override
    public void setAutoCacheState(boolean b) {
        mDataManager.setAutoCache(b);
    }

}

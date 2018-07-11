package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;

/**
 * Created by user on 2018/7/10.
 */

public interface SettingContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        boolean getAutoCacheState();

        boolean getNoImageState();

        boolean getNightModeState();

        void setNightModeState(boolean mode);

        void checkVersion(String versionName);

        void setNoImageState(boolean b);

        void setAutoCacheState(boolean b);
    }

}

package com.jthou.wanandroid.contract.main;

import com.jthou.wanandroid.base.BaseView;
import com.jthou.wanandroid.base.presenter.BasePresenter;
import com.jthou.wanandroid.model.entity.HotKey;

import java.util.List;

/**
 * Created by user on 2018/5/30.
 */

public interface SearchContract {

    interface View extends BaseView{

        void showHotKeyList(List<HotKey> hotWordList);

    }

    interface Presenter extends BasePresenter<View> {

        void getHotKeyList();

    }

}

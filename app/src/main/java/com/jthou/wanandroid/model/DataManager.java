package com.jthou.wanandroid.model;

import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.model.entity.ProjectClassify;
import com.jthou.wanandroid.model.network.AbstractResponse;
import com.jthou.wanandroid.model.network.BaseResponse;
import com.jthou.wanandroid.model.network.HttpHelper;
import com.jthou.wanandroid.model.sp.PreferenceHelper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DataManager implements DbHelper, HttpHelper, PreferenceHelper {

    private DbHelper mDbHelper;
    private HttpHelper mHttpHelper;
    private PreferenceHelper mPreferenceHelper;

    @Inject
    public DataManager(DbHelper mDbHelper, HttpHelper mHttpHelper, PreferenceHelper mPreferenceHelper) {
        this.mDbHelper = mDbHelper;
        this.mHttpHelper = mHttpHelper;
        this.mPreferenceHelper = mPreferenceHelper;
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getArticleList(int pageNum) {
        return mHttpHelper.getArticleList(pageNum);
    }

    @Override
    public Observable<AbstractResponse<List<Banner>>> getBannerData() {
        return mHttpHelper.getBannerData();
    }

    @Override
    public Observable<AbstractResponse<LoginInfo>> login(String username, String password) {
        return mHttpHelper.login(username, password);
    }

    @Override
    public Observable<AbstractResponse<List<KnowledgeHierarchy>>> getKnowledgeHierarchyList() {
        return mHttpHelper.getKnowledgeHierarchyList();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getFavoriteArticleList(int page) {
        return mHttpHelper.getFavoriteArticleList(page);
    }

    @Override
    public Observable<AbstractResponse<List<Navigation>>> getNavigationList() {
        return mHttpHelper.getNavigationList();
    }

    @Override
    public Observable<AbstractResponse<List<ProjectClassify>>> getProjectClassifyList() {
        return mHttpHelper.getProjectClassifyList();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getProjectListByClassify(int page, int cid) {
        return mHttpHelper.getProjectListByClassify(page, cid);
    }

    @Override
    public Observable<AbstractResponse<List<HotKey>>> getHotKeyList() {
        return mHttpHelper.getHotKeyList();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getSearchList(int page, String keyword) {
        return mHttpHelper.getSearchList(page, keyword);
    }

    @Override
    public boolean autoCache() {
        return mPreferenceHelper.autoCache();
    }

    @Override
    public boolean noImage() {
        return mPreferenceHelper.noImage();
    }

    @Override
    public boolean nightMode() {
        return mPreferenceHelper.nightMode();
    }

    @Override
    public void setAutoCache(boolean autoCache) {
        mPreferenceHelper.setAutoCache(autoCache);
    }

    @Override
    public void setNoImage(boolean noImage) {
        mPreferenceHelper.setNoImage(noImage);
    }

    @Override
    public void setNightMode(boolean nightMode) {
        mPreferenceHelper.setNightMode(nightMode);
    }

    @Override
    public void saveUsername(String username) {
        mPreferenceHelper.saveUsername(username);
    }

    @Override
    public void savePassword(String password) {
        mPreferenceHelper.savePassword(password);
    }

    @Override
    public String getUsername() {
        return mPreferenceHelper.getUsername();
    }

    @Override
    public String getPassword() {
        return mPreferenceHelper.getPassword();
    }

    @Override
    public void saveLoginState(boolean loginState) {
        mPreferenceHelper.saveLoginState(loginState);
    }

    @Override
    public boolean getLoginState() {
        return mPreferenceHelper.getLoginState();
    }

    @Override
    public void saveCurrentItem(int position) {
        mPreferenceHelper.saveCurrentItem(position);
    }

    @Override
    public int getCurrentItem() {
        return mPreferenceHelper.getCurrentItem();
    }

}

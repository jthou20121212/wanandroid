package com.jthou.wanandroid.model.network;

import com.jthou.wanandroid.model.api.WanAndroidApi;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.model.entity.ProjectClassify;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RetrofitHelper implements HttpHelper {

    private WanAndroidApi mWanAndroidApi;

    @Inject
    public RetrofitHelper(WanAndroidApi mWanAndroidApi) {
        this.mWanAndroidApi = mWanAndroidApi;
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getKnowledgeHierarchyArticleList(int pageNum) {
        return mWanAndroidApi.getArticleList(pageNum);
    }

    @Override
    public Observable<AbstractResponse<List<Banner>>> getBannerData() {
        return mWanAndroidApi.getBannerData();
    }

    @Override
    public Observable<AbstractResponse<LoginInfo>> login(String username, String password) {
        return mWanAndroidApi.login(username, password);
    }

    @Override
    public Observable<AbstractResponse<List<KnowledgeHierarchy>>> getKnowledgeHierarchyList() {
        return mWanAndroidApi.getKnowledgeHierarchyList();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getKnowledgeHierarchyArticleList(int page, int cid) {
        return mWanAndroidApi.getKnowledgeHierarchyArticleList(page, cid);
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getFavoriteArticleList(int page) {
        return mWanAndroidApi.getFavoriteArticleList(page);
    }

    @Override
    public Observable<AbstractResponse<List<Navigation>>> getNavigationList() {
        return mWanAndroidApi.getNavigationList();
    }

    @Override
    public Observable<AbstractResponse<List<ProjectClassify>>> getProjectClassifyList() {
        return mWanAndroidApi.getProjectClassifyData();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getProjectListByClassify(int page, int cid) {
        return mWanAndroidApi.getProjectList(page, cid);
    }

    @Override
    public Observable<AbstractResponse<List<HotKey>>> getHotKeyList() {
        return mWanAndroidApi.getHotKeyList();
    }

    @Override
    public Observable<AbstractResponse<BaseResponse<Article>>> getSearchList(int page, String keyword) {
        return mWanAndroidApi.getSearchList(page, keyword);
    }

    @Override
    public Observable<AbstractResponse<String>> collect(int articleId) {
        return mWanAndroidApi.favoriteArticle(articleId);
    }

    @Override
    public Observable<AbstractResponse<String>> cancelCollect(int articleId) {
        return mWanAndroidApi.cancelFavoriteArticle(articleId);
    }

}

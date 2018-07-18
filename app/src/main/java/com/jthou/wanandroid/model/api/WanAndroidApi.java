package com.jthou.wanandroid.model.api;

import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.model.entity.ProjectClassify;
import com.jthou.wanandroid.model.network.AbstractResponse;
import com.jthou.wanandroid.model.network.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 2018/5/17.
 */

public interface WanAndroidApi {

    String HOST = "http://www.wanandroid.com/";

    @GET("article/list/{page}/json")
    Observable<AbstractResponse<BaseResponse<Article>>> getArticleList(@Path("page") int page);

    @GET("banner/json")
    Observable<AbstractResponse<List<Banner>>> getBannerData();

    @POST("user/login")
    @FormUrlEncoded
    Observable<AbstractResponse<LoginInfo>> login(@Field("username") String username, @Field("password") String password);

    @GET("tree/json")
    Observable<AbstractResponse<List<KnowledgeHierarchy>>> getKnowledgeHierarchyList();

    @GET("lg/collect/list/{page}/json")
    Observable<AbstractResponse<BaseResponse<Article>>> getFavoriteArticleList(@Path("page") int page);

    @GET("navi/json")
    Observable<AbstractResponse<List<Navigation>>> getNavigationList();

    @GET("project/tree/json")
    Observable<AbstractResponse<List<ProjectClassify>>> getProjectClassifyData();

    @GET("project/list/{page}/json")
    Observable<AbstractResponse<BaseResponse<Article>>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    @GET("hotkey/json")
    Observable<AbstractResponse<List<HotKey>>> getHotKeyList();

    @POST("article/query/{page}/json")
    Observable<AbstractResponse<BaseResponse<Article>>> getSearchList(@Path("page") int page, @Query("k") String keyword);

    @GET("article/list/{page}/json")
    Observable<AbstractResponse<BaseResponse<Article>>> getKnowledgeHierarchyArticleList(@Path("page") int page, @Query("cid") int cid);

    @POST("lg/collect/{id}/json")
    Observable<AbstractResponse<String>> favoriteArticle(@Path("id") int articleId);

    @POST("lg/uncollect_originId/{id}/json")
    Observable<AbstractResponse<String>> cancelFavoriteArticle(@Path("id") int articleId);

}

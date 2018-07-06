package com.jthou.wanandroid.model.network;

import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.Banner;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.model.entity.ProjectClassify;

import java.util.List;

import io.reactivex.Observable;

public interface HttpHelper {

    // 获取文章列表
    Observable<AbstractResponse<BaseResponse<Article>>> getKnowledgeHierarchyArticleList(int pageNum);

    // 获取banner数据
    Observable<AbstractResponse<List<Banner>>> getBannerData();

    // 登录
    Observable<AbstractResponse<LoginInfo>> login(String username, String password);

    // 获取体系列表
    Observable<AbstractResponse<List<KnowledgeHierarchy>>> getKnowledgeHierarchyList();

    // 获取体系下文章列表
    Observable<AbstractResponse<BaseResponse<Article>>> getKnowledgeHierarchyArticleList(int page, int cid);

    // 获取收藏文章列表
    Observable<AbstractResponse<BaseResponse<Article>>> getFavoriteArticleList(int page);

    // 获取导航数据
    Observable<AbstractResponse<List<Navigation>>> getNavigationList();

    // 项目分类
    Observable<AbstractResponse<List<ProjectClassify>>> getProjectClassifyList();

    // 项目分类下列表数据
    Observable<AbstractResponse<BaseResponse<Article>>> getProjectListByClassify(int page, int cid);

    // 搜索热词
    Observable<AbstractResponse<List<HotKey>>> getHotKeyList();

    // 搜索
    Observable<AbstractResponse<BaseResponse<Article>>> getSearchList(int page, String keyword);
}

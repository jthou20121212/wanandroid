package com.jthou.wanandroid.model.entity;

import java.util.List;

/**
 * Created by user on 2018/5/29.
 */

public class Navigation {

    private List<Article> articles;
    private int cid;
    private String name;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

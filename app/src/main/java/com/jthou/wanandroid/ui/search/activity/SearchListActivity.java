package com.jthou.wanandroid.ui.search.activity;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.search.SearchContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.presenter.main.SearchPresenter;

import java.util.List;

public class SearchListActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @Override
    protected int resource() {
        return R.layout.activity_search_list;
    }

    @Override
    public void showSearchResult(List<Article> articleList) {

    }

}

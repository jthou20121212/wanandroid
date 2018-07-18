package com.jthou.wanandroid.di.moudle;

import com.jthou.wanandroid.di.component.ActivityComponent;
import com.jthou.wanandroid.ui.login.LoginActivity;
import com.jthou.wanandroid.ui.main.activity.ArticleDetailActivity;
import com.jthou.wanandroid.ui.main.activity.KnowledgeHierarchyDetailActivity;
import com.jthou.wanandroid.ui.main.activity.MainActivity;
import com.jthou.wanandroid.ui.search.activity.SearchListActivity;
import com.jthou.wanandroid.ui.welcome.activity.WelcomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = {ActivityComponent.class})
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract LoginActivity contributesLoginActivityInjector();

    @ContributesAndroidInjector
    abstract WelcomeActivity contributesWelcomeActivityInjector();

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector
    abstract ArticleDetailActivity contributesArticleDetailActivityInjector();

    @ContributesAndroidInjector
    abstract KnowledgeHierarchyDetailActivity contributesKnowledgeHierarchyDetailActivityInjector();

    @ContributesAndroidInjector
    abstract SearchListActivity contributesSearchListActivityInjector();

}

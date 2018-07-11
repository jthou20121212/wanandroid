package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.di.scope.FragmentScope;
import com.jthou.wanandroid.ui.main.fragment.FavoriteFragment;
import com.jthou.wanandroid.ui.main.fragment.HomePageFragment;
import com.jthou.wanandroid.ui.main.fragment.KnowledgeHierarchyFragment;
import com.jthou.wanandroid.ui.main.fragment.NavigationFragment;
import com.jthou.wanandroid.ui.main.fragment.ProjectFragment;
import com.jthou.wanandroid.ui.main.fragment.SearchFragment;
import com.jthou.wanandroid.ui.main.fragment.SettingFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(HomePageFragment fragment);

    void inject(KnowledgeHierarchyFragment fragment);

    void inject(FavoriteFragment fragment);

    void inject(NavigationFragment fragment);

    void inject(ProjectFragment fragment);

    void inject(SearchFragment fragment);

    void inject(SettingFragment fragment);

}

package com.jthou.wanandroid.di.moudle;

import com.jthou.wanandroid.di.component.FragmentComponent;
import com.jthou.wanandroid.ui.main.fragment.FavoriteFragment;
import com.jthou.wanandroid.ui.main.fragment.HomePageFragment;
import com.jthou.wanandroid.ui.main.fragment.KnowledgeHierarchyFragment;
import com.jthou.wanandroid.ui.main.fragment.NavigationFragment;
import com.jthou.wanandroid.ui.main.fragment.ProjectFragment;
import com.jthou.wanandroid.ui.main.fragment.SearchFragment;
import com.jthou.wanandroid.ui.main.fragment.SettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = FragmentComponent.class)
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract FavoriteFragment contributesFavoriteFragmentInject();

    @ContributesAndroidInjector
    abstract KnowledgeHierarchyFragment contributesKnowledgeHierarchyFragmentInject();

    @ContributesAndroidInjector
    abstract HomePageFragment contributesHomePageFragmentInject();

    @ContributesAndroidInjector
    abstract NavigationFragment contributesNavigationFragmentInject();

    @ContributesAndroidInjector
    abstract ProjectFragment contributesProjectFragmentInject();

    @ContributesAndroidInjector
    abstract SettingFragment contributesSettingFragmentInject();

    @ContributesAndroidInjector
    abstract SearchFragment contributesSearchFragmentInject();

}

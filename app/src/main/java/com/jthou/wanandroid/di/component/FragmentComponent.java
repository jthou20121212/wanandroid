package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.base.fragment.BaseFragment;
import com.jthou.wanandroid.di.scope.FragmentScope;
import com.jthou.wanandroid.ui.main.fragment.FavoriteFragment;
import com.jthou.wanandroid.ui.main.fragment.HomePageFragment;
import com.jthou.wanandroid.ui.main.fragment.KnowledgeHierarchyFragment;
import com.jthou.wanandroid.ui.main.fragment.NavigationFragment;
import com.jthou.wanandroid.ui.main.fragment.ProjectFragment;
import com.jthou.wanandroid.ui.main.fragment.SearchFragment;
import com.jthou.wanandroid.ui.main.fragment.SettingFragment;

import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface FragmentComponent extends AndroidInjector<BaseFragment> {

    /**
     * 每一个继承于BaseFragment的Fragment都继承于同一个子组件
     */
    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<BaseFragment>{

    }
}

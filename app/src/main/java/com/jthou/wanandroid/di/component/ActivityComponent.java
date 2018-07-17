package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.di.scope.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface ActivityComponent extends AndroidInjector<BaseActivity> {

    /**
     * 每一个继承于BaseActivity的Activity都继承于同一个子组件
     */
    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<BaseActivity>{

    }

}

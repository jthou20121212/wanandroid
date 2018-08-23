package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.di.moudle.ActivityModule;
import com.jthou.wanandroid.di.moudle.AppModule;
import com.jthou.wanandroid.di.moudle.FragmentModule;
import com.jthou.wanandroid.di.moudle.HttpModule;
import com.jthou.wanandroid.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class,
        AppModule.class, HttpModule.class, ActivityModule.class, FragmentModule.class})
public interface AppComponent {

    void inject(WanAndroidApp application);

    DataManager getDataManager();

}

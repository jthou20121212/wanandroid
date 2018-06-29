package com.jthou.wanandroid.di.component;

import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.di.moudle.AppModule;
import com.jthou.wanandroid.di.moudle.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    DataManager getDataManager();

}

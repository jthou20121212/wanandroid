package com.jthou.wanandroid.di.moudle;

import com.jthou.wanandroid.model.DataManager;
import com.jthou.wanandroid.model.DbHelper;
import com.jthou.wanandroid.model.sp.PreferenceHelper;
import com.jthou.wanandroid.model.network.HttpHelper;
import com.jthou.wanandroid.model.network.RetrofitHelper;
import com.jthou.wanandroid.model.sp.PreferenceHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    DbHelper provideDbHelper() {
        return null;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper httpHelper) {
        return httpHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferenceHelper(PreferenceHelperImpl preferenceHelper) {
        return preferenceHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, PreferenceHelper preferenceHelper) {
        return new DataManager(null, httpHelper, preferenceHelper);
    }

}

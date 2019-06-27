package com.jthou.wanandroid.app;

import android.app.Activity;
import android.app.Application;

import com.jthou.wanandroid.di.component.AppComponent;
import com.jthou.wanandroid.di.component.DaggerAppComponent;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WanAndroidApp extends TinkerApplication implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    private static Application instance;
    private static volatile AppComponent appComponent;

    public WanAndroidApp() {
//        参数1：tinkerFlags 表示Tinker支持的类型 dex only、library only or all suuport，default: TINKER_ENABLE_ALL
//        参数2：delegateClassName Application代理类 这里填写你自定义的ApplicationLike
//        参数3：loaderClassName Tinker的加载器，使用默认即可
//        参数4：tinkerLoadVerifyFlag 加载dex或者lib是否验证md5，默认为false
        super(ShareConstants.TINKER_ENABLE_ALL, "com.jthou.wanandroid.tinker.ApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.create();
        }
        return appComponent;
    }

//    static {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        getAppComponent().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

}

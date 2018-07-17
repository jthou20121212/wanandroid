package com.jthou.wanandroid.app;

import android.app.Activity;
import android.app.Application;

import com.jthou.wanandroid.BuildConfig;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.di.component.AppComponent;
import com.jthou.wanandroid.di.component.DaggerAppComponent;
import com.jthou.wanandroid.util.image.GlideImageProvider;
import com.jthou.wanandroid.util.image.ImageLoader;
import com.jthou.wanandroid.util.image.ImageProvider;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class WanAndroidApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;

    private static WanAndroidApp instance;
    private static volatile AppComponent appComponent;

//    static {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//    }

    public static synchronized WanAndroidApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        ImageProvider provider = new GlideImageProvider();
        ImageLoader.setImageProvider(provider);

        getAppComponent();
        appComponent.inject(this);

        initLogger();
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.create();
        }
        return appComponent;
    }

    private void initLogger() {
        //DEBUG版本才打控制台log
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                    tag(getString(R.string.app_name)).build()));
        }
//        //把log存到本地
//        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().
//                tag(getString(R.string.app_name)).build(getPackageName(), getString(R.string.app_name))));
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }

}

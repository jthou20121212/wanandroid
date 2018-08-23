package com.jthou.wanandroid.tinker;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.jthou.wanandroid.BuildConfig;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.util.LogHelper;
import com.jthou.wanandroid.util.image.GlideImageProvider;
import com.jthou.wanandroid.util.image.ImageLoader;
import com.jthou.wanandroid.util.image.ImageProvider;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

public class ApplicationLike extends DefaultApplicationLike {

    public ApplicationLike(Application application, int tinkerFlags,
                           boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                           long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    private void initLogger() {
        //DEBUG版本才打控制台log
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().
                    tag(getApplication().getString(R.string.app_name)).build()));
        }
//        //把log存到本地
//        Logger.addLogAdapter(new DiskLogAdapter(TxtFormatStrategy.newBuilder().
//                tag(getString(R.string.app_name)).build(getPackageName(), getString(R.string.app_name))));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageProvider provider = new GlideImageProvider();
        ImageLoader.setImageProvider(provider);

        initLogger();

        Bugly.init(getApplication(), "88c59bf0e3", true);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

}
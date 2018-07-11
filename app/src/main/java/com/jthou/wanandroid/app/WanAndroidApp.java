package com.jthou.wanandroid.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

import com.jthou.wanandroid.BuildConfig;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.di.component.AppComponent;
import com.jthou.wanandroid.di.component.DaggerAppComponent;
import com.jthou.wanandroid.di.moudle.AppModule;
import com.jthou.wanandroid.di.moudle.HttpModule;
import com.jthou.wanandroid.util.L;
import com.jthou.wanandroid.util.image.GlideImageProvider;
import com.jthou.wanandroid.util.image.ImageLoader;
import com.jthou.wanandroid.util.image.ImageProvider;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class WanAndroidApp extends Application {

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

        initLogger();
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule())
                    .httpModule(new HttpModule())
                    .build();
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

}

package com.jthou.wanandroid.ui.welcome.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.WanAndroidApp;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.welcome.WelcomeContract;
import com.jthou.wanandroid.di.component.DaggerActivityComponent;
import com.jthou.wanandroid.presenter.welcome.WelcomePresenter;
import com.jthou.wanandroid.ui.main.activity.MainActivity;
import com.jthou.wanandroid.util.StatusBarUtil;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerActivityComponent.builder().appComponent(WanAndroidApp.getAppComponent()).build().inject(this);
        super.onCreate(savedInstanceState);
        StatusBarUtil.immersive(this);
    }

    @Override
    protected int resource() {
        return R.layout.activity_welcome;
    }

    @Override
    public void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

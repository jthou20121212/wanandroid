package com.jthou.wanandroid.ui.welcome.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.welcome.WelcomeContract;
import com.jthou.wanandroid.presenter.welcome.WelcomePresenter;
import com.jthou.wanandroid.ui.main.activity.MainActivity;
import com.jthou.wanandroid.util.StatusBarUtil;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

package com.jthou.wanandroid.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.login.LoginContract;
import com.jthou.wanandroid.model.entity.LoginInfo;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.presenter.login.LoginPresenter;
import com.jthou.wanandroid.ui.register.RegisterActivity;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.RxBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.id_et_username)
    TextView mTvUsername;
    @BindView(R.id.id_et_password)
    TextView mTvPassword;

    @Override
    protected int resource() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.id_login)
    public void login(View v) {
        String username = mTvUsername.getText().toString().trim();
        String password = mTvPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
            return;
        }
        mPresenter.login(username, password);
    }

    @Override
    public void showLoginInfo(LoginInfo loginInfo) {
        RxBus.getDefault().post(new LoginEvent());
        onBackPressedSupport();
    }

    @OnClick(R.id.id_register)
    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
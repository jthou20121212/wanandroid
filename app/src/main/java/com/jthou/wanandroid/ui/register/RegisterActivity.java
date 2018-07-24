package com.jthou.wanandroid.ui.register;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.activity.BaseActivity;
import com.jthou.wanandroid.contract.register.RegisterContract;
import com.jthou.wanandroid.model.entity.RegisterResult;
import com.jthou.wanandroid.model.event.LoginEvent;
import com.jthou.wanandroid.presenter.register.RegisterPresenter;
import com.jthou.wanandroid.ui.login.LoginActivity;
import com.jthou.wanandroid.util.ActivityContainer;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.RxBus;
import com.jthou.wanandroid.util.RxUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.id_ace_username)
    TextView mAceUserName;
    @BindView(R.id.id_ace_password)
    TextView mAcePassword;
    @BindView(R.id.id_ace_repassword)
    TextView mAceRePassword;

    @Override
    protected int resource() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.id_acb_register)
    public void register(View v) {
        String account = mAceUserName.getText().toString().trim();
        String password = mAcePassword.getText().toString().trim();
        String rePassword = mAceRePassword.getText().toString().trim();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
            return;
        }

        if (!password.equals(rePassword)) {
            CommonUtils.showSnackMessage(this, getString(R.string.password_not_same));
            return;
        }

        mPresenter.register(account, password, rePassword);
    }

    @Override
    public void handleRegisterResult() {
        ActivityContainer.getInstance().finishActivity(LoginActivity.class);
        onBackPressedSupport();
    }

}

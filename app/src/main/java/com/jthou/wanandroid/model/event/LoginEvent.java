package com.jthou.wanandroid.model.event;

/**
 * Created by user on 2018/5/25.
 */

public class LoginEvent {

    private boolean isAutoLogin;

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
    }
}

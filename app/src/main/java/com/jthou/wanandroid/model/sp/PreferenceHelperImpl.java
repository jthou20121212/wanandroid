package com.jthou.wanandroid.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.app.WanAndroidApp;

import javax.inject.Inject;

/**
 * Created by user on 2018/5/22.
 */

public class PreferenceHelperImpl implements PreferenceHelper {

    private static final String MY_SHARED_PREFERENCE = "wan_android";
    private final SharedPreferences mPreferences;

    @Inject
    PreferenceHelperImpl() {
        mPreferences = WanAndroidApp.getInstance().getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public boolean autoCache() {
        return mPreferences.getBoolean(Key.SP_AUTO_CACHE, false);
    }

    @Override
    public boolean noImage() {
        return mPreferences.getBoolean(Key.SP_NO_IMAGE, false);
    }

    @Override
    public boolean nightMode() {
        return mPreferences.getBoolean(Key.SP_NIGHT_MODE, false);
    }

    @Override
    public void setAutoCache(boolean autoCache) {
        mPreferences.edit().putBoolean(Key.SP_AUTO_CACHE, autoCache).apply();
    }

    @Override
    public void setNoImage(boolean noImage) {
        mPreferences.edit().putBoolean(Key.SP_NO_IMAGE, noImage).apply();
    }

    @Override
    public void setNightMode(boolean nightMode) {
        mPreferences.edit().putBoolean(Key.SP_NIGHT_MODE, nightMode).apply();
    }

    @Override
    public void saveUsername(String username) {
        mPreferences.edit().putString(Key.SP_USERNAME, username).apply();
    }

    @Override
    public void savePassword(String password) {
        mPreferences.edit().putString(Key.SP_PASSWORD, password).apply();
    }

    @Override
    public String getUsername() {
        return mPreferences.getString(Key.SP_USERNAME, "");
    }

    @Override
    public String getPassword() {
        return mPreferences.getString(Key.SP_PASSWORD, "");
    }

    @Override
    public void saveLoginState(boolean loginState) {
        mPreferences.edit().putBoolean(Key.SP_LOGIN_STATE, loginState).apply();
    }

    @Override
    public boolean getLoginState() {
        return mPreferences.getBoolean(Key.SP_LOGIN_STATE, false);
    }

    @Override
    public void saveCurrentItem(int position) {
        mPreferences.edit().putInt(Key.SP_CURRENT_ITEM, position).apply();
    }

    @Override
    public int getCurrentItem() {
        return mPreferences.getInt(Key.SP_CURRENT_ITEM, 0);
    }

    @Override
    public void logout() {
        mPreferences.edit().remove(Key.SP_LOGIN_STATE).remove(Key.SP_USERNAME).remove(Key.SP_PASSWORD).apply();
    }

}

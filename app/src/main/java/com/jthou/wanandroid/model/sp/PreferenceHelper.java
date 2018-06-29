package com.jthou.wanandroid.model.sp;

/**
 * Created by user on 2018/5/16.
 */

public interface PreferenceHelper {

    boolean autoCache();

    boolean noImage();

    boolean nightMode();

    void setAutoCache(boolean autoCache);

    void setNoImage(boolean noImage);

    void setNightMode(boolean nightMode);

    void saveUsername(String username);

    void savePassword(String password);

    String getUsername();

    String getPassword();

    void saveLoginState(boolean loginState);

    boolean getLoginState();

    void saveCurrentItem(int position);

    int getCurrentItem();

}

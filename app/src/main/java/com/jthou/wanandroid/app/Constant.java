package com.jthou.wanandroid.app;

import java.io.File;

/**
 * Created by user on 2018/5/16.
 */

public class Constant {

    public static final int NORMAL_STATE = 0;
    public static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;

    public static final String PATH_DATA = WanAndroidApp.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final int TYPE_MAIN_PAGER = 0;
    public static final int TYPE_KNOWLEDGE = 1;
    public static final int TYPE_NAVIGATION = 2;
    public static final int TYPE_PROJECT = 3;
    public static final int TYPE_COLLECT = 4;
    public static final int TYPE_SETTING = 5;


}

package com.jthou.wanandroid.util;

import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 处理暴力点击
 */
public class AntiShakeUtils {

    static volatile AntiShakeUtils singleton = null;

    private AntiShakeUtils() {

    }

    public static AntiShakeUtils getInstance() {
        if (singleton == null) {
            synchronized (AntiShakeUtils.class) {
                if (singleton == null) {
                    singleton = new AntiShakeUtils();
                }
            }
        }
        return singleton;
    }

    private static final int TIME = 1000;

    private Map<String, WeakHashMap<View, Long>> mViews = new HashMap<>();

    /**
     * @param name Fragment或者Activity的getClass().getName()
     * @param v    点击的控件
     * @return TIME时间内是不是点击过
     */
    public boolean click(String name, View v) {
        WeakHashMap<View, Long> viewMap = mViews.get(name);
        long currentTime = System.currentTimeMillis();
        if (viewMap == null) {
            viewMap = new WeakHashMap<>();
            viewMap.put(v, currentTime);
            mViews.put(name, viewMap);
            return false;
        }
        long lastTime = viewMap.get(v) == null ? 0 : viewMap.get(v);
        if (currentTime - lastTime > TIME) {
            viewMap.put(v, currentTime);
            return false;
        }
        return true;
    }

    public void remove(String name) {
        WeakHashMap<View, Long> viewMap = mViews.get(name);
        if (viewMap == null) return;
        mViews.remove(name);
        viewMap.clear();
        viewMap = null;
    }

}

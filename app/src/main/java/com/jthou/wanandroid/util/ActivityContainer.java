package com.jthou.wanandroid.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2016/5/20.
 * Activity管理工具类
 */
public class ActivityContainer {

    private List<Activity> mActivityList;

    private static volatile ActivityContainer sInstance;

    private ActivityContainer() {
        this.mActivityList = new LinkedList<>();
    }

    public static ActivityContainer getInstance() {
        if (sInstance == null) {
            synchronized (ActivityContainer.class) {
                if (sInstance == null) {
                    sInstance = new ActivityContainer();
                }
            }
        }
        return sInstance;
    }

    public void addActivity(Activity a) {
        mActivityList.add(a);
    }

    public void removeActivity(Activity a) {
        mActivityList.remove(a);
    }

    /**
     * 关闭指定的activity
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz) {
        if (clazz == null) return;
        Iterator<Activity> iterator = mActivityList.listIterator();
        while (iterator.hasNext()) {
            Activity a = iterator.next();
            if (a == null) {
                iterator.remove();
                continue;
            }
            if (clazz == a.getClass()) {
                a.finish();
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 关闭所有activity
     */
    public void finishAllActivity() {
        Iterator<Activity> iterator = mActivityList.listIterator();
        while (iterator.hasNext()) {
            Activity a = iterator.next();
            if (a == null) {
                iterator.remove();
                continue;
            }
            a.finish();
        }
    }

}

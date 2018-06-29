package com.jthou.wanandroid.model.annotation;

import android.support.annotation.IntDef;

import com.jthou.wanandroid.app.Constant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Constant.NORMAL_STATE, Constant.LOADING_STATE, Constant.ERROR_STATE})
@Retention(RetentionPolicy.SOURCE)
public @interface State {}

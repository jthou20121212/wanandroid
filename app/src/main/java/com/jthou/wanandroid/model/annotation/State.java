package com.jthou.wanandroid.model.annotation;

import android.support.annotation.IntDef;

import com.jthou.wanandroid.app.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Constants.NORMAL_STATE, Constants.LOADING_STATE, Constants.ERROR_STATE})
@Retention(RetentionPolicy.SOURCE)
public @interface State {}

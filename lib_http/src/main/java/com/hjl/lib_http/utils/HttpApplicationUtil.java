package com.hjl.lib_http.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Application类 初始化各种配置
 */
public class HttpApplicationUtil extends Application {

    private static Context mContext;//全局上下文对象

    public static class httpCode{
        public static int success = 0;
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void init(Context context) {
        mContext = context;
    }


}

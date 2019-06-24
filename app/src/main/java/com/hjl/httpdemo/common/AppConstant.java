package com.hjl.httpdemo.common;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hjl.httpdemo.utils.ApplicationUtil;

public class AppConstant {
    private static final String TAG = "AppConstant";


    public static String PARAM_KEY_VERSION = "v"; //接口
    public static String API_VERSION_V1 = "1"; //接口版本号


    public static String SERVER_ADDRESS = "";

    public static String getServerAddress() {
        if (SERVER_ADDRESS.equals("")) {
            //配置信息
            try {
                ApplicationInfo appInfo = ApplicationUtil.getContext().getPackageManager()
                        .getApplicationInfo(ApplicationUtil.getContext().getPackageName(),
                                PackageManager.GET_META_DATA);
                //获取服务器地址
                AppConstant.SERVER_ADDRESS = appInfo.metaData.getString("APP_URL");
                Log.d(TAG, "服务器地址: " + AppConstant.SERVER_ADDRESS);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return AppConstant.SERVER_ADDRESS;
    }


    public static int PageSize = 30; //列表每页显示的数量


}

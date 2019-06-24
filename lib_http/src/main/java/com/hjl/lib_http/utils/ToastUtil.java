package com.hjl.lib_http.utils;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import retrofit2.http.HTTP;


public class ToastUtil {


    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 显示short message
     * @param message 显示msg
     */
    public static void showShort(String message) {
        Toast toast = Toast.makeText(HttpApplicationUtil.getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    /**
     * 显示long message
     * @param message 显示msg
     */
    public static void showLong(String message) {
        Toast toast = Toast.makeText(HttpApplicationUtil.getContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}

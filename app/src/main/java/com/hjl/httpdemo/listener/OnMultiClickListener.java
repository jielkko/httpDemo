package com.hjl.httpdemo.listener;

import android.util.Log;
import android.view.View;

/**
 * Android防止过快点击造成多次事件执行（防止按钮重复点击）
 * Created by Administrator on 2018/1/26.
 */

public abstract class OnMultiClickListener implements View.OnClickListener{
    private static String TAG = "OnMultiClickListener";
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final long MIN_CLICK_DELAY_TIME = 1000L;
    private static long lastClickTime;

    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if(curClickTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onMultiClick(v);
        }else{
            Log.d(TAG, "不能多次点击");
        }
    }
}

//使用例子
/*btn.setOnClickListener(new OnMultiClickListener() {
@Override
public void onMultiClick(View v) {
        // 进行点击事件后的逻辑操作
        }
        });*/

package com.hjl.lib_http.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hjl.lib_http.R;


public class DialogLoading {

    ProgressBar mLoadingView;
    Dialog mDialogLoading;

    public DialogLoading(Context context, String msg) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_dialog_loading, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading_view);
        // 页面中显示文本
        TextView loadingText = (TextView) view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mDialogLoading = new Dialog(context, R.style.dialog_loading_style);
        // 设置返回键无效
        //mDialogLoading.setCancelable(false);
        mDialogLoading.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


    }


    public void setCancelable(Boolean isCancelable) {
        if (isCancelable) {
            mDialogLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (mOnCancelListener != null) {
                        mOnCancelListener.onCancelClick();
                    }
                }
            });

        }

    }

    public Boolean isShowing() {
       return mDialogLoading.isShowing();
    }

    public void show() {
        mDialogLoading.show();
        //mLoadingView.startAnim();
    }

    public void dismiss() {
        if (mDialogLoading != null) {
            //mLoadingView.stopAnim();
            mDialogLoading.dismiss();
            //mDialogLoading=null;
        }
    }

    //点击
    private OnCancelListener mOnCancelListener;

    public interface OnCancelListener {
        void onCancelClick();
    }

    public void setOnCancelListener(OnCancelListener mOnCancelListener) {
        this.mOnCancelListener = mOnCancelListener;
    }


}

package com.hjl.lib_http.progress;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hjl.lib_http.view.DialogLoading;


/**
 * Dialog的进度控制
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private DialogLoading sad;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (sad == null) {
            sad = new DialogLoading(context,"加载中");
            sad.setCancelable(cancelable);

            if (cancelable) {
                sad.setOnCancelListener(new DialogLoading.OnCancelListener() {
                    @Override
                    public void onCancelClick() {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!sad.isShowing()) {
                sad.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (sad != null) {
            sad.dismiss();
            sad = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}

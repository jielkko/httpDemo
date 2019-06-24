package com.hjl.httpdemo.activity.login;



import com.hjl.lib_http.base.BasePresenter;
import com.hjl.lib_http.base.BaseResponse;
import com.hjl.lib_http.base.BaseView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableTransformer;

/**
 * LoginContract  V 、P契约类
 */
public interface LoginContract {

    interface View extends BaseView {

        void result(BaseResponse<LoginBean> data);

        void logoutResult(BaseResponse<List<LoginBean>> data);

        void getUserList(BaseResponse<List<UserBean>> data);

        void getChaptersResult(BaseResponse<List<LoginBean>> data);

        void setMsg(String msg);

        <T> ObservableTransformer<T, T> bindLifecycle();

    }

    abstract class Presenter extends BasePresenter<View> {

        //请求1
        public abstract void login(HashMap<String, String> map, boolean isDialog, boolean cancelable);

        //用户列表
        public abstract void getUserList(HashMap<String, String> map, boolean isDialog, boolean cancelable);

        //请求2
        public abstract void logout(HashMap<String, String> map, boolean isDialog, boolean cancelable);

        //请求3
        public abstract void getChapters(boolean isDialog, boolean cancelable);
    }
}

